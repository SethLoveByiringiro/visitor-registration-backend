package com.moh.visitorregistration.repository;

import com.moh.visitorregistration.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    List<Visitor> findByNamesContainingIgnoreCase(String name);
    Optional<Visitor> findByIdNumberAndVisitDate(String idNumber, LocalDate visitDate);
}
