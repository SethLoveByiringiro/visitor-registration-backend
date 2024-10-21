package com.moh.visitorregistration.service;

import com.moh.visitorregistration.model.Visitor;
import com.moh.visitorregistration.repository.VisitorRepository;
import com.moh.visitorregistration.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorService {
    private final VisitorRepository visitorRepository;

    @Autowired
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public Visitor registerVisitor(Visitor visitor) {
        Optional<Visitor> existingVisitor = visitorRepository.findByIdNumberAndVisitDate(visitor.getIdNumber(), visitor.getVisitDate());
        if (existingVisitor.isPresent()) {
            throw new IllegalStateException("Visitor with this ID has already been registered for this date.");
        }

        return visitorRepository.save(visitor);
    }

    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    public List<Visitor> searchVisitorsByName(String name) {
        return visitorRepository.findByNamesContainingIgnoreCase(name);
    }

    public Visitor recordDeparture(Long id, LocalTime departureTime) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found with id: " + id));
        visitor.setDepartureTime(departureTime);
        return visitorRepository.save(visitor);
    }

    public Visitor updateVisitor(Long id, Visitor visitorDetails) {
        Visitor visitor = visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found with id: " + id));

        visitor.setNames(visitorDetails.getNames());
        visitor.setIdNumber(visitorDetails.getIdNumber());
        visitor.setPhone(visitorDetails.getPhone());
        visitor.setPurpose(visitorDetails.getPurpose());
        visitor.setDepartmentToVisit(visitorDetails.getDepartmentToVisit());
        // Don't update visitDate, arrivalTime, or departureTime here

        return visitorRepository.save(visitor);
    }

}