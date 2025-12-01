package com.edprs.edprs_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_reports")
public class MedicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One medical report is linked to a single emergency case
    @OneToOne
    @JoinColumn(name = "case_id", nullable = false)
    private casee emergencyCase;

    // -------------------- PATIENT INFO --------------------

    private String patientName;
    private Integer age;
    private String gender; // Expected values: Male, Female

    // -------------------- VITAL SIGNS --------------------

    private Integer pulse;        // Heart rate (beats per minute)
    private String bloodPressure; // Format example: 120/80
    private Double temperature;   // Body temperature in Celsius
    private Integer o2Saturation; // Oxygen saturation percentage (SpO2)

    // -------------------- CLINICAL DETAILS --------------------

    @Column(length = 1000)
    private String chiefComplaint; // Main reason for the call / primary symptom
    
    @Column(length = 2000)
    private String interventions;  // Treatments and procedures performed by the paramedic
    
    private String outcome;        // e.g. Transported, Treated on scene, Refused transport
    
    @Column(length = 2000)
    private String paramedicNotes; // Free-text notes and additional observations

    private LocalDateTime submittedAt; // When the report was completed and submitted

    // -------------------- GETTERS & SETTERS --------------------

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public casee getEmergencyCase() { 
        return emergencyCase; 
    }
    public void setEmergencyCase(casee emergencyCase) { 
        this.emergencyCase = emergencyCase; 
    }

    public String getPatientName() { 
        return patientName; 
    }
    public void setPatientName(String patientName) { 
        this.patientName = patientName; 
    }

    public Integer getAge() { 
        return age; 
    }
    public void setAge(Integer age) { 
        this.age = age; 
    }

    public String getGender() { 
        return gender; 
    }
    public void setGender(String gender) { 
        this.gender = gender; 
    }

    public Integer getPulse() { 
        return pulse; 
    }
    public void setPulse(Integer pulse) { 
        this.pulse = pulse; 
    }

    public String getBloodPressure() { 
        return bloodPressure; 
    }
    public void setBloodPressure(String bloodPressure) { 
        this.bloodPressure = bloodPressure; 
    }

    public Double getTemperature() { 
        return temperature; 
    }
    public void setTemperature(Double temperature) { 
        this.temperature = temperature; 
    }

    public Integer getO2Saturation() { 
        return o2Saturation; 
    }
    public void setO2Saturation(Integer o2Saturation) { 
        this.o2Saturation = o2Saturation; 
    }

    public String getChiefComplaint() { 
        return chiefComplaint; 
    }
    public void setChiefComplaint(String chiefComplaint) { 
        this.chiefComplaint = chiefComplaint; 
    }

    public String getInterventions() { 
        return interventions; 
    }
    public void setInterventions(String interventions) { 
        this.interventions = interventions; 
    }

    public String getOutcome() { 
        return outcome; 
    }
    public void setOutcome(String outcome) { 
        this.outcome = outcome; 
    }

    public String getParamedicNotes() { 
        return paramedicNotes; 
    }
    public void setParamedicNotes(String paramedicNotes) { 
        this.paramedicNotes = paramedicNotes; 
    }

    public LocalDateTime getSubmittedAt() { 
        return submittedAt; 
    }
    public void setSubmittedAt(LocalDateTime submittedAt) { 
        this.submittedAt = submittedAt; 
    }
}
