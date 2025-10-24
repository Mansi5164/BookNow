package com.cabbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String bookingNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "cab_id")
    private Cab cab;

    // Pickup location
    @Column(nullable = false)
    private String pickupAddress;

    @Column(nullable = false)
    private Double pickupLatitude;

    @Column(nullable = false)
    private Double pickupLongitude;

    // Drop-off location
    @Column(nullable = false)
    private String dropoffAddress;

    @Column(nullable = false)
    private Double dropoffLatitude;

    @Column(nullable = false)
    private Double dropoffLongitude;

    @Column(nullable = false)
    private LocalDateTime requestedTime;

    private LocalDateTime acceptedTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.REQUESTED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CabType requestedCabType;

    private BigDecimal estimatedFare;
    private BigDecimal actualFare;
    private BigDecimal distance; // in kilometers
    private Integer estimatedDuration; // in minutes
    private Integer actualDuration; // in minutes

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String specialInstructions;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum BookingStatus {
        REQUESTED, ACCEPTED, DRIVER_ASSIGNED, DRIVER_ARRIVED,
        IN_PROGRESS, COMPLETED, CANCELLED
    }

    public enum CabType {
        SEDAN, HATCHBACK, SUV, LUXURY
    }

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED
    }

    public enum PaymentMethod {
        CASH, CARD, WALLET, UPI
    }
}