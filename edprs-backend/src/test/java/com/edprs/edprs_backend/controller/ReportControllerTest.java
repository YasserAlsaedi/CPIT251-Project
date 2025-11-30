package com.edprs.edprs_backend.controller;

import com.edprs.edprs_backend.model.MedicalReport;
import com.edprs.edprs_backend.model.casee; 
import com.edprs.edprs_backend.model.unit;  

import com.edprs.edprs_backend.repository.CaseRepository;
import com.edprs.edprs_backend.repository.ReportRepository;
import com.edprs.edprs_backend.repository.UnitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private CaseRepository caseRepository;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private ReportController reportController;

    @Test
    public void testSubmitReport_Success() {
        // 1. DATA SETUP 
        Long caseId = 100L;
        
        // Create a busy Unit
        unit mockUnit = new unit();
        mockUnit.setId(50L);
        mockUnit.setCode("AMB-01");
        mockUnit.setStatus("ON_MISSION"); 

        // Create an Active Case assigned to that Unit
        casee mockCase = new casee();
        mockCase.setId(caseId);
        mockCase.setCaseNumber("CASE-TEST-001");
        mockCase.setStatus("Accepted");
        mockCase.setAssignedUnit(mockUnit); 

        // Create the Report Data (Input)
        MedicalReport incomingReport = new MedicalReport();
        incomingReport.setBloodPressure("120/80");
        incomingReport.setPulse(80);
        incomingReport.setOutcome("Transported");
        incomingReport.setParamedicNotes("Patient stable.");

        // 2. MOCK DATABASE RESPONSES 
        when(caseRepository.findById(caseId)).thenReturn(Optional.of(mockCase));
        when(reportRepository.save(any(MedicalReport.class))).thenAnswer(i -> i.getArguments()[0]);

        // 3. EXECUTE THE METHOD 
        MedicalReport result = reportController.submitReport(caseId, incomingReport);

        //  4. VERIFY RESULTS 
        
        // Check 1: Report was returned/saved
        assertNotNull(result);
        assertEquals("Transported", result.getOutcome());

        // Check 2: Case should now be "Completed"
        assertEquals("Completed", mockCase.getStatus());
        verify(caseRepository).save(mockCase); // Ensure it was saved to DB

        // Check 3: Unit should now be "READY" (Automatic Logic)
        assertEquals("READY", mockUnit.getStatus());
        verify(unitRepository).save(mockUnit); // Ensure unit was updated in DB

        // Check 4: Dashboard was notified via WebSocket
        verify(messagingTemplate).convertAndSend(eq("/topic/dashboard-updates"), anyString());
    }
    
  
}