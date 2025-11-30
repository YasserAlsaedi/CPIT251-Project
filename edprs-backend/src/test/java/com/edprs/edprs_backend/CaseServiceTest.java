package com.edprs.edprs_backend;

import com.edprs.edprs_backend.model.casee; 
import com.edprs.edprs_backend.model.unit;  
import com.edprs.edprs_backend.repository.CaseRepository;
import com.edprs.edprs_backend.repository.UnitRepository;
import com.edprs.edprs_backend.service.CaseService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CaseServiceTest {

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private CaseService caseService;

    @Test
    public void testUpdateStatus() {
        // 1. Setup
        Long caseId = 10L;
        casee mockCase = new casee();
        mockCase.setId(caseId);
        mockCase.setStatus("Pending");

        // 2. Mock
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(mockCase));
        when(caseRepository.save(any(casee.class))).thenAnswer(i -> i.getArguments()[0]);

        // 3. Execute
        casee result = caseService.updateStatus(caseId, "Accepted");

        // 4. Verify
        assertEquals("Accepted", result.getStatus());
        verify(caseRepository).save(mockCase);
    }
    
    @Test
    public void testGetActiveCases() {
        // 1. Setup Data
        casee c1 = new casee(); 
        c1.setStatus("Pending");
        
        casee c2 = new casee(); 
        c2.setStatus("Assigned");
        
        java.util.List<casee> mockList = java.util.Arrays.asList(c1, c2);

        // 2. Mock Database Behavior
        // When the service asks for cases that are NOT "Completed", return our mock list
        when(caseRepository.findByStatusNot("Completed")).thenReturn(mockList);

        // 3. Run Logic
        java.util.List<casee> result = caseService.getActiveCases();

        // 4. Verify Results
        assertEquals(2, result.size()); // We expect 2 cases
        verify(caseRepository).findByStatusNot("Completed"); // Ensure the DB was called correctly
    }
    
    
    @Test
    public void testAcceptCase_Success() {
        // 1. SETUP
        Long caseId = 10L;
        Long unitId = 5L;

        casee mockCase = new casee();
        mockCase.setId(caseId);
        mockCase.setStatus("Pending");

        unit mockUnit = new unit();
        mockUnit.setId(unitId);
        mockUnit.setStatus("READY");

        // 2. MOCK
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(mockCase));
        when(unitRepository.findById(unitId)).thenReturn(Optional.of(mockUnit));
        when(caseRepository.save(any(casee.class))).thenAnswer(i -> i.getArguments()[0]);

        // 3. EXECUTE
        casee result = caseService.acceptCase(caseId, unitId);

        // 4. VERIFY
        assertEquals("Accepted", result.getStatus());       // Case should be Accepted
        assertEquals("ON_MISSION", mockUnit.getStatus());   // Unit should be ON_MISSION
        assertEquals(mockUnit, result.getAssignedUnit());   // Unit should be linked
        
        // Verify DB saves and Alert sent
        verify(unitRepository).save(mockUnit);
        verify(messagingTemplate).convertAndSend(eq("/topic/unit-alerts"), eq("CASE TAKEN"));
    }
    
 
    
    @Test
    public void testDeleteCase() {
        // 1. SETUP
        Long caseId = 50L;
        casee mockCase = new casee();
        mockCase.setId(caseId);
        
        // Create a fake unit attached to it (so we can test if it gets freed)
        unit mockUnit = new unit();
        mockUnit.setCode("AMB-01");
        mockUnit.setStatus("ON_MISSION");
        mockCase.setAssignedUnit(mockUnit);

        // 2. MOCK THE FIND (This is what you were missing!)
        // When the service asks "Does Case 50 exist?", say "YES, here it is."
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(mockCase));

        // 3. EXECUTE
        caseService.deleteCase(caseId);

        // 4. VERIFY
        // Did we set the unit back to READY?
        assertEquals("READY", mockUnit.getStatus());
        verify(unitRepository).save(mockUnit);
        
        // Did we actually delete the case?
        verify(caseRepository).delete(mockCase);
        
        // Did we notify the dashboard?
        verify(messagingTemplate).convertAndSend(eq("/topic/dashboard-updates"), anyString());
    }
}
