package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import static com.cabbooking.util.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {

    private Long id;
    private String bookingNumber;

    @Size(max = MAX_STATUS_LENGTH, message = "Status must be " + MAX_STATUS_LENGTH + " characters or fewer")
    private String status;

    @Size(max = MAX_ADDRESS_LENGTH, message = "Pickup address must be " + MAX_ADDRESS_LENGTH + " characters or fewer")
    private String pickupAddress;

    @Size(max = MAX_ADDRESS_LENGTH, message = "Dropoff address must be " + MAX_ADDRESS_LENGTH + " characters or fewer")
    private String dropoffAddress;

    @DecimalMin(value = "" + MIN_FARE, message = "Estimated fare cannot be negative")
    private BigDecimal estimatedFare;

    @DecimalMin(value = "" + MIN_FARE, message = "Actual fare cannot be negative")
    private BigDecimal actualFare;

    @DecimalMin(value = "" + MIN_DISTANCE, message = "Distance cannot be negative")
    private BigDecimal distance;

    @Min(value = MIN_DURATION, message = "Estimated duration cannot be negative")
    private Integer estimatedDuration;

    @Min(value = MIN_DURATION, message = "Actual duration cannot be negative")
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
