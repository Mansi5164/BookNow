package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {

    private Long id;
    private String bookingNumber;
    @Size(max = 50, message = "Status must be 50 characters or fewer")
    private String status;
    @Size(max = 255, message = "Pickup address must be 255 characters or fewer")
    private String pickupAddress;
    @Size(max = 255, message = "Dropoff address must be 255 characters or fewer")
    private String dropoffAddress;
    @DecimalMin(value = "0.0", message = "Estimated fare cannot be negative")
    private BigDecimal estimatedFare;
    @DecimalMin(value = "0.0", message = "Actual fare cannot be negative")
    private BigDecimal actualFare;
    @DecimalMin(value = "0.0", message = "Distance cannot be negative")
    private BigDecimal distance;
    @Min(value = 0, message = "Estimated duration cannot be negative")
    private Integer estimatedDuration;
    @Min(value = 0, message = "Actual duration cannot be negative")
    private Integer actualDuration;
    private LocalDateTime requestedTime;
    private LocalDateTime acceptedTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String paymentStatus;
    private String paymentMethod;
    private UserSummaryDto user;
    private DriverSummaryDto driver;
    private CabSummaryDto cab;
}