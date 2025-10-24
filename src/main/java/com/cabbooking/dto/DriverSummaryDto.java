package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import static com.cabbooking.util.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverSummaryDto {

    private Long id;

    @Size(max = MAX_NAME_LENGTH, message = "License number must be " + MAX_NAME_LENGTH + " characters or fewer")
    private String licenseNumber;

    @DecimalMin(value = MIN_RATING_STRING, message = "Rating cannot be less than " + MIN_RATING_STRING)
    @DecimalMax(value = MAX_RATING_STRING, message = "Rating cannot exceed " + MAX_RATING_STRING)
    private Double rating;

    @Min(value = MIN_TOTAL_RIDES, message = "Total rides cannot be less than " + MIN_TOTAL_RIDES)
    private Integer totalRides;

    @Size(max = MAX_NAME_LENGTH, message = "Status must be " + MAX_NAME_LENGTH + " characters or fewer")
    private String status;

    @Size(max = MAX_NAME_LENGTH, message = "Verification status must be " + MAX_NAME_LENGTH + " characters or fewer")
    private String verificationStatus;

    @DecimalMin(value = MIN_LATITUDE_STRING, message = "Current latitude must be >= " + MIN_LATITUDE_STRING)
    @DecimalMax(value = MAX_LATITUDE_STRING, message = "Current latitude must be <= " + MAX_LATITUDE_STRING)
    private Double currentLatitude;

    @DecimalMin(value = MIN_LONGITUDE_STRING, message = "Current longitude must be >= " + MIN_LONGITUDE_STRING)
    @DecimalMax(value = MAX_LONGITUDE_STRING, message = "Current longitude must be <= " + MAX_LONGITUDE_STRING)
    private Double currentLongitude;

    private UserSummaryDto user;
}
