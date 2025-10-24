package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabSummaryDto {

    private Long id;
    private String licensePlate;
    @Size(max = 50, message = "Make must be 50 characters or fewer")
    private String make;
    @Size(max = 50, message = "Model must be 50 characters or fewer")
    private String model;
    @Size(max = 30, message = "Color must be 30 characters or fewer")
    private String color;
    @Min(value = 1900, message = "Year must be a valid year")
    @Max(value = 2100, message = "Year must be a valid year")
    private Integer year;
    @Size(max = 50, message = "Cab type must be 50 characters or fewer")
    private String cabType;
    @Size(max = 50, message = "Status must be 50 characters or fewer")
    private String status;
    @Min(value = 1, message = "Seating capacity must be at least 1")
    private Integer seatingCapacity;
}