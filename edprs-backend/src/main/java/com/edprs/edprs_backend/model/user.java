package com.edprs.edprs_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "users") // "users" is better because "user" is a reserved SQL word
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore // CRITICAL: Never send the password hash to the frontend!
    private String password;

    // Roles: ROLE_SUPERVISOR, ROLE_PARAMEDIC
    @Column(nullable = false)
    private String role;

    // Link to a specific ambulance unit (e.g., "AMB-01")
    private String unitCode;

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUnitCode() { return unitCode; }
    public void setUnitCode(String unitCode) { this.unitCode = unitCode; }
}
