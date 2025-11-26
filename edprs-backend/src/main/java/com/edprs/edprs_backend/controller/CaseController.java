package com.edprs.edprs_backend.controller;

import com.edprs.edprs_backend.model.casee; // Or 'casee'
import com.edprs.edprs_backend.service.CaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
@CrossOrigin(origins = "*")
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    // 1. ADD CASE (POST)
    @PostMapping
    @PreAuthorize("hasRole('SUPERVISOR')")
    public casee createCase(@RequestBody casee c) {
        return caseService.createCase(c);
    }

    // 2. CHECK CASES - ALL (GET)
    @GetMapping
    public List<casee> getAllCases() {
        return caseService.getAllCases();
    }

    // 3. CHECK CASES - ACTIVE ONLY (GET)
    @GetMapping("/active")
    public List<casee> getActive() {
        return caseService.getActiveCases();
    }

    // 4. UPDATE STATUS (PUT)
 // ... inside CaseController ...

    @PutMapping("/{id}/accept")
    public casee acceptCase(@PathVariable Long id, @RequestParam Long unitId) {
        return caseService.acceptCase(id, unitId);
    }

    // 5. EDIT CASE DETAILS (PUT)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public casee updateDetails(@PathVariable Long id, @RequestBody casee c) {
        return caseService.updateCaseDetails(id, c);
    }
    
 // --- NEW: REJECT ENDPOINT ---
    @PutMapping("/{id}/reject")
    public void rejectCase(@PathVariable Long id) {
        caseService.rejectCase(id);
    }

    // 6. DELETE CASE (DELETE)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public void deleteCase(@PathVariable Long id) {
        caseService.deleteCase(id);
    }
}