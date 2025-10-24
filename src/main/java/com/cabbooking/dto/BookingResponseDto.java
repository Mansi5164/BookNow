package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {

    private Long id;
    private String bookingNumber;
    private String status;
    private String pickupAddress;
    private String dropoffAddress;
    private BigDecimal estimatedFare;
    private BigDecimal actualFare;
    private BigDecimal distance;
    private Integer estimatedDuration;
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