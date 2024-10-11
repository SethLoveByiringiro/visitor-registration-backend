package com.moh.visitorregistration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "visitors")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String names;

    @NotBlank(message = "ID number is required")
    @Column(nullable = false)
    @Size(min = 16, max = 16, message = "ID number must be exactly 16 digits")
    @Pattern(regexp = "\\d{16}", message = "ID number must contain exactly 16 digits")
    private String idNumber;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,14}$", message = "Invalid phone number format")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Purpose of visit is required")
    @Column(nullable = false)
    private String purpose;

    @NotNull(message = "Arrival time is required")
    @Column(nullable = false)
    private LocalTime arrivalTime;

    private LocalTime departureTime;

    @NotNull(message = "Visit date is required")
    @Column(nullable = false)
    private LocalDate visitDate;

    @NotBlank(message = "Department to visit is required")
    @Column(nullable = false)
    private String departmentToVisit;

    @PrePersist
    protected void onCreate() {
        if (visitDate == null) {
            visitDate = LocalDate.now();
        }
        if (arrivalTime == null) {
            arrivalTime = LocalTime.now();
        }
    }
}