package com.moh.visitorregistration.controller;

import com.moh.visitorregistration.model.Visitor;
import com.moh.visitorregistration.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




@CrossOrigin(origins = "http://10.5.163.50:3000")

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerVisitor(@Valid @RequestBody Visitor visitor) {
        try {
            Visitor registeredVisitor = visitorService.registerVisitor(visitor);
            return ResponseEntity.ok(registeredVisitor);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Visitor>> searchVisitors(@RequestParam String name) {
        List<Visitor> visitors = visitorService.searchVisitorsByName(name);
        return ResponseEntity.ok(visitors);
    }

    @GetMapping
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        return ResponseEntity.ok(visitors);
    }



    @PutMapping("/{id}/departure")
    public ResponseEntity<Visitor> recordDeparture(@PathVariable Long id) {
        Visitor updatedVisitor = visitorService.recordDeparture(id);
        return ResponseEntity.ok(updatedVisitor);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}