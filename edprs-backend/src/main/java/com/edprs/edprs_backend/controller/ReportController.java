package com.edprs.edprs_backend.controller;

import com.edprs.edprs_backend.model.casee;
import com.edprs.edprs_backend.model.MedicalReport;
import com.edprs.edprs_backend.model.unit;
import com.edprs.edprs_backend.repository.CaseRepository;
import com.edprs.edprs_backend.repository.ReportRepository;
import com.edprs.edprs_backend.repository.UnitRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportRepository reportRepository;
    private final CaseRepository caseRepository;
    private final UnitRepository unitRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ReportController(ReportRepository reportRepo, CaseRepository caseRepo, UnitRepository unitRepo, SimpMessagingTemplate msg) {
        this.reportRepository = reportRepo;
        this.caseRepository = caseRepo;
        this.unitRepository = unitRepo;
        this.messagingTemplate = msg;
    }

    @GetMapping
    public List<MedicalReport> getAllReports() {
        return reportRepository.findAll();
    }

    @PostMapping("/{caseId}")
    @Transactional
    public MedicalReport submitReport(@PathVariable Long caseId, @RequestBody MedicalReport d) {
    	casee c = caseRepository.findById(caseId).orElseThrow(() -> new RuntimeException("Case not found"));

        MedicalReport r = new MedicalReport();
        r.setEmergencyCase(c);
        // Map NEW fields
        r.setPatientName(d.getPatientName());
        r.setAge(d.getAge());
        r.setGender(d.getGender());
        r.setPulse(d.getPulse());
        r.setBloodPressure(d.getBloodPressure());
        r.setTemperature(d.getTemperature());
        r.setO2Saturation(d.getO2Saturation());
        r.setChiefComplaint(d.getChiefComplaint());
        r.setInterventions(d.getInterventions());
        r.setOutcome(d.getOutcome());
        r.setParamedicNotes(d.getParamedicNotes());
        
        r.setSubmittedAt(LocalDateTime.now());
        MedicalReport saved = reportRepository.save(r);

        // Close Case
        c.setStatus("Completed");
        caseRepository.save(c);

        if (c.getAssignedUnit() != null) {
            unit u = c.getAssignedUnit();
            u.setStatus("READY");
            unitRepository.save(u);
            messagingTemplate.convertAndSend("/topic/dashboard-updates", "Report Received");
        }
        return saved;
    }

    @PutMapping("/{id}")
    public MedicalReport updateReport(@PathVariable Long id, @RequestBody MedicalReport d) {
        MedicalReport r = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
        
        // Update ALL fields
        r.setPatientName(d.getPatientName());
        r.setAge(d.getAge());
        r.setGender(d.getGender());
        r.setPulse(d.getPulse());
        r.setBloodPressure(d.getBloodPressure());
        r.setTemperature(d.getTemperature());
        r.setO2Saturation(d.getO2Saturation());
        r.setChiefComplaint(d.getChiefComplaint());
        r.setInterventions(d.getInterventions());
        r.setOutcome(d.getOutcome());
        r.setParamedicNotes(d.getParamedicNotes());
        
        return reportRepository.save(r);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        reportRepository.deleteById(id);
    }
}