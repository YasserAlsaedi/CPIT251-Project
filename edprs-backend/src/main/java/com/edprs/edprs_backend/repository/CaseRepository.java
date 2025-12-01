package com.edprs.edprs_backend.repository;

import com.edprs.edprs_backend.model.casee; 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CaseRepository extends JpaRepository<casee, Long> {
    // Find active cases (e.g., status != "COMPLETED")
    List<casee> findByStatusNot(String status);
    
    // Find specific case
    Optional<casee> findByCaseNumber(String caseNumber);
}

