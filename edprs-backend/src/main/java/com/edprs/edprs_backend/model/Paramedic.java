package com.edprs.edprs_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Paramedic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnore // Important: Never send passwords over API
    private String password; // Stored as plain text for simplicity in this prototype

    private String name;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private unit unit; // Link the paramedic to their assigned unit

    // Getters and Setters (Omitted for brevity, but required)
    // You should generate these in your IDE (IntelliJ/Eclipse)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public unit getUnit() { return unit; }
    public void setUnit(unit unit) { this.unit = unit; }
}