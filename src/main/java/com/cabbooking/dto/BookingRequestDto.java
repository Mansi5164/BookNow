package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Pickup address is required")
    private String pickupAddress;

    @NotNull(message = "Pickup latitude is required")
    private Double pickupLatitude;

    @NotNull(message = "Pickup longitude is required")
    private Double pickupLongitude;

    @NotBlank(message = "Dropoff address is required")
    private String dropoffAddress;

    @NotNull(message = "Dropoff latitude is required")
    private Double dropoffLatitude;

    @NotNull(message = "Dropoff longitude is required")
    private Double dropoffLongitude;

    @NotBlank(message = "Requested cab type is required")
    private String requestedCabType;

    private String specialInstructions;

    private String paymentMethod;
}