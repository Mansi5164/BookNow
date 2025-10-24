package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotBlank(message = "Pickup address is required")
    @Size(max = 255, message = "Pickup address must be 255 characters or fewer")
    private String pickupAddress;

    @NotNull(message = "Pickup latitude is required")
    @DecimalMin(value = "-90.0", message = "Pickup latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Pickup latitude must be <= 90")
    private Double pickupLatitude;

    @NotNull(message = "Pickup longitude is required")
    @DecimalMin(value = "-180.0", message = "Pickup longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Pickup longitude must be <= 180")
    private Double pickupLongitude;

    @NotBlank(message = "Dropoff address is required")
    @Size(max = 255, message = "Dropoff address must be 255 characters or fewer")
    private String dropoffAddress;

    @NotNull(message = "Dropoff latitude is required")
    @DecimalMin(value = "-90.0", message = "Dropoff latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Dropoff latitude must be <= 90")
    private Double dropoffLatitude;

    @NotNull(message = "Dropoff longitude is required")
    @DecimalMin(value = "-180.0", message = "Dropoff longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Dropoff longitude must be <= 180")
    private Double dropoffLongitude;

    @NotBlank(message = "Requested cab type is required")
    @Size(max = 50, message = "Requested cab type must be 50 characters or fewer")
    private String requestedCabType;

    @Size(max = 500, message = "Special instructions must be 500 characters or fewer")
    private String specialInstructions;

    @Size(max = 50, message = "Payment method must be 50 characters or fewer")
    private String paymentMethod;
}