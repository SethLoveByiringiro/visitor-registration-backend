package com.moh.visitorregistration.service;

import com.moh.visitorregistration.model.Visitor;
import com.moh.visitorregistration.repository.VisitorRepository;
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

        visitor.setArrivalTime(LocalTime.now());
        return visitorRepository.save(visitor);
    }

    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    public List<Visitor> searchVisitorsByName(String name) {
        return visitorRepository.findByNamesContainingIgnoreCase(name);
    }

    public Visitor recordDeparture(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        visitor.setDepartureTime(LocalTime.now());
        return visitorRepository.save(visitor);
    }


}