package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabSummaryDto {

    private Long id;
    private String licensePlate;
    private String make;
    private String model;
    private String color;
    private Integer year;
    private String cabType;
    private String status;
    private Integer seatingCapacity;
}