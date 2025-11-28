package com.edprs.edprs_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "units")
public class unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; 

    // READY / ON_MISSION / NOT_READY
    @Column(nullable = false)
    private String status;
    
    //  Real-World GPS Tracking 
    private Double latitude;
    private Double longitude;
    
    // To track if the unit has gone offline (Signal Lost)
    private LocalDateTime lastLocationUpdate;

    //  Getters and Setters 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public LocalDateTime getLastLocationUpdate() { return lastLocationUpdate; }
    public void setLastLocationUpdate(LocalDateTime lastLocationUpdate) { 
        this.lastLocationUpdate = lastLocationUpdate; 
    }
}

