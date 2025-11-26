package com.edprs.edprs_backend.repository;

import com.edprs.edprs_backend.model.unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<unit, Long> {

    unit findByCode(String code);

    List<unit> findByStatus(String status);
}

