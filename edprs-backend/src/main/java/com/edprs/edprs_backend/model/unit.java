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
    private String code;   // Unique unit identifier (e.g., AMB-01, RRT-07)

    // Current operational state of the unit
    // Possible values: READY / ON_MISSION / NOT_READY
    @Column(nullable = false)
    private String status;
    
    // Live GPS location (latitude & longitude) reported from the unit
    private Double latitude;
    private Double longitude;
    
    // Timestamp of last location update — used to detect if the unit goes offline
    private LocalDateTime lastLocationUpdate;

    // ------------------- Getters & Setters -------------------

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getCode() { 
        return code; 
    }
    public void setCode(String code) { 
        this.code = code; 
    }

    public String getStatus() { 
        return status; 
    }
    public void setStatus(String status) { 
        this.status = status; 
    }

    public Double getLatitude() { 
        return latitude; 
    }
    public void setLatitude(Double latitude) { 
        this.latitude = latitude; 
    }

    public Double getLongitude() { 
        return longitude; 
    }
    public void setLongitude(Double longitude) { 
        this.longitude = longitude; 
    }
    
    public LocalDateTime getLastLocationUpdate() { 
        return lastLocationUpdate; 
    }
    public void setLastLocationUpdate(LocalDateTime lastLocationUpdate) { 
        this.lastLocationUpdate = lastLocationUpdate; 
    }
}
