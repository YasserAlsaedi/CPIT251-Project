package com.edprs.edprs_backend.controller;

import com.edprs.edprs_backend.model.casee; 
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

    // 1. CREATE NEW CASE (POST) – supervisor only
    @PostMapping
    @PreAuthorize("hasRole('SUPERVISOR')")
    public casee createCase(@RequestBody casee c) {
        return caseService.createCase(c);
    }

    // 2. GET ALL CASES (GET) – returns every case in the system
    @GetMapping
    public List<casee> getAllCases() {
        return caseService.getAllCases();
    }

    // 3. GET ONLY ACTIVE CASES (GET) – used for currently open cases
    @GetMapping("/active")
    public List<casee> getActive() {
        return caseService.getActiveCases();
    }

    // 4. ACCEPT CASE (PUT) – assign case to a specific unit
    @PutMapping("/{id}/accept")
    public casee acceptCase(@PathVariable Long id, @RequestParam Long unitId) {
        return caseService.acceptCase(id, unitId);
    }

    // 5. UPDATE CASE DETAILS (PUT) – supervisor can edit case information
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public casee updateDetails(@PathVariable Long id, @RequestBody casee c) {
        return caseService.updateCaseDetails(id, c);
    }
    
    // 6. REJECT CASE (PUT) – mark case as rejected
    @PutMapping("/{id}/reject")
    public void rejectCase(@PathVariable Long id) {
        caseService.rejectCase(id);
    }

    // 7. DELETE CASE (DELETE) – supervisor can completely remove a case
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public void deleteCase(@PathVariable Long id) {
        caseService.deleteCase(id);
    }
}
