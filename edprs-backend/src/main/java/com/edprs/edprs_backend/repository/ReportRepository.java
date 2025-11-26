package com.edprs.edprs_backend.repository;

import com.edprs.edprs_backend.model.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<MedicalReport, Long> {
}