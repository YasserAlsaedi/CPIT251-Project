package com.edprs.edprs_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cases")
public class casee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // رقم حالة مقروء (CASE-...)
    @Column(nullable = false, unique = true)
    private String caseNumber;

    // Emergency / Patient Transport
    @Column(nullable = false)
    private String type;

    // Critical / High / Medium / Low
    @Column(nullable = false)
    private String urgency;

    private String mrn;
    private String callerPhone;
    private String location;

    @Column(length = 2000)
    private String description;

    private boolean relativePresent;

    // Pending / Assigned / Completed
    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "assigned_unit_id")
    private unit assignedUnit;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getMrn() {
        return mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public String getCallerPhone() {
        return callerPhone;
    }

    public void setCallerPhone(String callerPhone) {
        this.callerPhone = callerPhone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRelativePresent() {
        return relativePresent;
    }

    public void setRelativePresent(boolean relativePresent) {
        this.relativePresent = relativePresent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public unit getAssignedUnit() {
        return assignedUnit;
    }

    public void setAssignedUnit(unit assignedUnit) {
        this.assignedUnit = assignedUnit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
 // ... existing fields ...

    private String llocation; // Text description

    // --- NEW: Exact GPS Location ---
    private Double latitude;
    private Double longitude;

    // --- Add Getters and Setters ---
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
