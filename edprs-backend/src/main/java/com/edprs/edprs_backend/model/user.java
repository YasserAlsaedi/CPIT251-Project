package com.edprs.edprs_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "users") 
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique username used for login
    @Column(unique = true, nullable = false)
    private String username;

    // Encrypted password (BCrypt hash)
    // Marked @JsonIgnore so it is never exposed in API responses
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    // Application role for authorization:
    // e.g. ROLE_SUPERVISOR, ROLE_PARAMEDIC
    @Column(nullable = false)
    private String role;

    // Optional: code of the unit this user belongs to (e.g., AMB-01)
    private String unitCode;

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

    public String getRole() { 
        return role; 
    }
    public void setRole(String role) { 
        this.role = role; 
    }

    public String getUnitCode() { 
        return unitCode; 
    }
    public void setUnitCode(String unitCode) { 
        this.unitCode = unitCode; 
    }
} 
