package com.edprs.edprs_backend.repository;

import com.edprs.edprs_backend.model.Paramedic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParamedicRepository extends JpaRepository<Paramedic, Long> {
    // Custom query method for authentication
    Optional<Paramedic> findByUsername(String username);
}
