package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverSummaryDto {

    private Long id;
    @Size(max = 50, message = "License number must be 50 characters or fewer")
    private String licenseNumber;
    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0")
    private Double rating;
    @Min(value = 0, message = "Total rides cannot be negative")
    private Integer totalRides;
    @Size(max = 50, message = "Status must be 50 characters or fewer")
    private String status;
    @Size(max = 50, message = "Verification status must be 50 characters or fewer")
    private String verificationStatus;
    @DecimalMin(value = "-90.0", message = "Current latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Current latitude must be <= 90")
    private Double currentLatitude;
    @DecimalMin(value = "-180.0", message = "Current longitude must be >= -180")
    @DecimalMax(value = "180.0", message = "Current longitude must be <= 180")
    private Double currentLongitude;
    private UserSummaryDto user;
}