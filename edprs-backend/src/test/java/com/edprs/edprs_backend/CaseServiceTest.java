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
    public void testCreateCase_AssignsUnitIfSelected() {
        // 1. SETUP
        unit mockUnit = new unit();
        mockUnit.setId(1L);
        mockUnit.setCode("AMB-01");
        mockUnit.setStatus("READY");

        casee newCase = new casee();
        newCase.setDescription("Test Emergency");
        newCase.setAssignedUnit(mockUnit); // Supervisor picked this unit

        // 2. MOCK
        when(unitRepository.findById(1L)).thenReturn(Optional.of(mockUnit));
        when(caseRepository.save(any(casee.class))).thenAnswer(i -> i.getArguments()[0]);

        // 3. EXECUTE
        casee result = caseService.createCase(newCase);

        // 4. VERIFY
        assertNotNull(result.getCaseNumber()); 
        assertEquals("Assigned", result.getStatus()); 
        assertEquals("ON_MISSION", mockUnit.getStatus()); 
        
        verify(unitRepository, times(1)).save(mockUnit);
    }

    @Test
    public void testCreateCase_BroadcastIfNoUnit() {
        // 1. SETUP
        casee newCase = new casee();
        newCase.setDescription("Broadcast Alert");
        newCase.setAssignedUnit(null);

        when(caseRepository.save(any(casee.class))).thenAnswer(i -> i.getArguments()[0]);

        // 2. EXECUTE
        casee result = caseService.createCase(newCase);

        // 3. VERIFY
        assertEquals("Pending", result.getStatus()); 
        assertNull(result.getAssignedUnit()); 
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
}
