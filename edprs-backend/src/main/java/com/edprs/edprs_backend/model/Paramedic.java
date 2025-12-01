package com.edprs.edprs_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Paramedic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Login username for the paramedic
    private String username;

    // Password (ignored in JSON responses to protect credentials)
    // Note: Stored as plain text in this prototype, NOT secure for production
    @JsonIgnore
    private String password;

    // Full name of the paramedic
    private String name;

    // Assigned unit (e.g., ambulance or response team)
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private unit unit;

    // ------------------- Getters & Setters -------------------

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public unit getUnit() { 
        return unit; 
    }
    public void setUnit(unit unit) { 
        this.unit = unit; 
    }
}
