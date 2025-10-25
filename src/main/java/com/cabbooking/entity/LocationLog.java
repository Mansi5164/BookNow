package com.cabbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "location_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Driver is required")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Latitude is required")
    private Double latitude;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Longitude is required")
    private Double longitude;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Speed is required")
    private Double speed;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Heading is required")
    private Double heading;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime timestamp;
}