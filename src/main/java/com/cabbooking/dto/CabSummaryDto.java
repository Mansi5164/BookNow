package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import static com.cabbooking.util.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabSummaryDto {

    private Long id;
    private String licensePlate;

    @Size(max = MAX_NAME_LENGTH, message = "Make must be " + MAX_NAME_LENGTH + " characters or fewer")
    private String make;

    @Size(max = MAX_NAME_LENGTH, message = "Model must be " + MAX_NAME_LENGTH + " characters or fewer")
    private String model;

    @Size(max = MAX_COLOR_LENGTH, message = "Color must be " + MAX_COLOR_LENGTH + " characters or fewer")
    private String color;

    @Min(value = MIN_YEAR, message = "Year must be a valid year")
    @Max(value = MAX_YEAR, message = "Year must be a valid year")
    private Integer year;

    @Size(max = MAX_NAME_LENGTH, message = "Cab type must be " + MAX_NAME_LENGTH + " characters or fewer")
    private String cabType;

    @Size(max = MAX_NAME_LENGTH, message = "Status must be " + MAX_NAME_LENGTH + " characters or fewer")
    private String status;

    @Min(value = MIN_SEATING_CAPACITY, message = "Seating capacity must be at least " + MIN_SEATING_CAPACITY)
    private Integer seatingCapacity;
}
