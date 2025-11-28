package com.edprs.edprs_backend.service;

import com.edprs.edprs_backend.model.casee; 
import com.edprs.edprs_backend.model.unit;        
import com.edprs.edprs_backend.repository.CaseRepository;
import com.edprs.edprs_backend.repository.UnitRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CaseService {

    private final CaseRepository caseRepository;
    private final UnitRepository unitRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public CaseService(CaseRepository caseRepository, UnitRepository unitRepository, SimpMessagingTemplate messagingTemplate) {
        this.caseRepository = caseRepository;
        this.unitRepository = unitRepository;
        this.messagingTemplate = messagingTemplate;
    }

    //  1. GET ALL CASES 
    public List<casee> getAllCases() {
        return caseRepository.findAll();
    }
    
    //  2. CREATE 
    @Transactional
    public casee createCase(casee c) {
        c.setCaseNumber("CASE-" + System.currentTimeMillis());
        c.setCreatedAt(LocalDateTime.now());

        // 1. CHECK: Did Supervisor select a specific unit?
        if (c.getAssignedUnit() != null && c.getAssignedUnit().getId() != null) {
            unit selected = unitRepository.findById(c.getAssignedUnit().getId()).orElse(null);
            if (selected != null) {
                c.setAssignedUnit(selected);
                c.setStatus("Assigned"); // Specific Assignment
                selected.setStatus("ON_MISSION");
                unitRepository.save(selected);
            }
        } else {
            // 2. NO UNIT SELECTED -> BROADCAST MODE
            c.setAssignedUnit(null);
            c.setStatus("Pending"); // Visible to ALL units
        }

        casee savedCase = caseRepository.save(c);
        
        // Notify everyone (Tablets will check if they should show it)
        messagingTemplate.convertAndSend("/topic/unit-alerts", "NEW CASE AVAILABLE");
        messagingTemplate.convertAndSend("/topic/dashboard-updates", "New Case Created");
        
        return savedCase;
    }
    
    
    @Transactional
    public casee acceptCase(Long caseId, Long unitId) {
    	casee c = caseRepository.findById(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));
        
        unit u = unitRepository.findById(unitId)
            .orElseThrow(() -> new RuntimeException("Unit not found"));

        // CRITICAL: Check if someone else took it first!
        if (c.getAssignedUnit() != null && !c.getAssignedUnit().getId().equals(unitId)) {
            throw new RuntimeException("Case already taken by another unit!");
        }

        // Winner takes the case
        c.setAssignedUnit(u);
        c.setStatus("Accepted");
        
        // Lock the unit
        u.setStatus("ON_MISSION");
        unitRepository.save(u);
        
        // Notify everyone (So the alert disappears for others)
        messagingTemplate.convertAndSend("/topic/unit-alerts", "CASE TAKEN");
        messagingTemplate.convertAndSend("/topic/dashboard-updates", "Case Accepted");

        return caseRepository.save(c);
    }

    public List<casee> getActiveCases() {
        return caseRepository.findByStatusNot("Completed");
    }

    @Transactional
    public casee updateStatus(Long id, String status) {
    	casee c = caseRepository.findById(id).orElseThrow(() -> new RuntimeException("Case not found"));
        c.setStatus(status);

        if ("Completed".equalsIgnoreCase(status) && c.getAssignedUnit() != null) {
            unit u = c.getAssignedUnit();
            u.setStatus("READY");
            unitRepository.save(u);
            messagingTemplate.convertAndSend("/topic/unit-alerts", "Mission Complete. Unit " + u.getCode() + " is now READY.");
        }
        return caseRepository.save(c);
    }

    //  3. EDIT CASE DETAILS 
    public casee updateCaseDetails(Long id, casee updatedData) {
    	casee c = caseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Case not found ID: " + id));

        // Update fields
        c.setDescription(updatedData.getDescription());
        c.setLocation(updatedData.getLocation());
        c.setUrgency(updatedData.getUrgency());
        c.setType(updatedData.getType());
        // We do NOT update 'status' or 'assignedUnit' here (use updateStatus for that)
        
        return caseRepository.save(c);
    }

 //  4. DELETE CASE (Updated with Real-Time Alert) 
    @Transactional
    public void deleteCase(Long id) {
    	casee c = caseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Case not found ID: " + id));

        // 1. Free the Unit (if assigned)
        if (c.getAssignedUnit() != null) {
            unit u = c.getAssignedUnit();
            u.setStatus("READY"); 
            unitRepository.save(u);
            
            // Notify the Unit Tablet (so they know the mission is cancelled)
            messagingTemplate.convertAndSend("/topic/unit-alerts", 
                "Mission Cancelled. Unit " + u.getCode() + " is now READY.");
        }

        // 2. Delete the Case from DB
        caseRepository.delete(c);

        // 3. NOTIFY DASHBOARD (This fixes your Refresh issue!)
        // This sends a signal to dashboard.html to run "loadInitialData()" again
        messagingTemplate.convertAndSend("/topic/dashboard-updates", "Case Deleted");
    }
    
 //  REJECT LOGIC 
    @Transactional
    public void rejectCase(Long caseId) {
    	casee c = caseRepository.findById(caseId)
            .orElseThrow(() -> new RuntimeException("Case not found"));

        // 1. Free the Unit
        if (c.getAssignedUnit() != null) {
            unit u = c.getAssignedUnit();
            u.setStatus("READY");
            unitRepository.save(u);
            
            messagingTemplate.convertAndSend("/topic/unit-alerts", 
                "Unit " + u.getCode() + " REJECTED Case " + c.getCaseNumber());
        }

        // 2. Reset Case to Pending (So others can take it)
        c.setAssignedUnit(null);
        c.setStatus("Pending");
        caseRepository.save(c);

        // 3. Refresh Dashboard
        messagingTemplate.convertAndSend("/topic/dashboard-updates", "Case Rejected");
    }
}