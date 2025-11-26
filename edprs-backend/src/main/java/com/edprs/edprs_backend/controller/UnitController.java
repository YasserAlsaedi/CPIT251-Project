package com.edprs.edprs_backend.controller;

import com.edprs.edprs_backend.model.unit;
import com.edprs.edprs_backend.repository.UnitRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate; // <--- NEW IMPORT
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/units")
@CrossOrigin(origins = "*")
public class UnitController {

    private final UnitRepository unitRepository;
    private final SimpMessagingTemplate messagingTemplate; // <--- NEW TOOL

    // Constructor Injection
    public UnitController(UnitRepository unitRepository, SimpMessagingTemplate messagingTemplate) {
        this.unitRepository = unitRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<unit> getAll() {
        return unitRepository.findAll();
    }

    @PostMapping
    public unit create(@RequestBody unit unit) {
        if (unit.getStatus() == null) {
            unit.setStatus("READY");
        }
        return unitRepository.save(unit);
    }

    // --- UPDATED: STATUS CHANGE (Triggers Refresh) ---
    @PutMapping("/{id}/status")
    public unit updateStatus(@PathVariable Long id, @RequestParam String status) {
        unit u = unitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Unit not found"));
        
        u.setStatus(status);
        unit savedUnit = unitRepository.save(u);

        // 🔔 ALERT THE DASHBOARD
        messagingTemplate.convertAndSend("/topic/dashboard-updates", "Unit Status Changed: " + status);

        return savedUnit;
    }

    // --- UPDATED: GPS CHANGE (Triggers Map Move) ---
    @PutMapping("/{id}/location")
    public unit updateLocation(@PathVariable Long id, 
                               @RequestParam Double lat, 
                               @RequestParam Double lon) {
        unit u = unitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Unit not found"));
            
        u.setLatitude(lat);
        u.setLongitude(lon);
        u.setLastLocationUpdate(LocalDateTime.now());
        
        unit savedUnit = unitRepository.save(u);
        
        // 🔔 ALERT THE DASHBOARD (So the icon moves instantly)
        // We send to a specific channel so we don't reload the WHOLE list, just the map markers if we wanted optimization.
        // For now, reloading the dashboard is fine.
        messagingTemplate.convertAndSend("/topic/dashboard-updates", "Unit Moved");
        
        return savedUnit;
    }
}
