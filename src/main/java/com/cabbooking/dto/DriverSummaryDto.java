package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverSummaryDto {

    private Long id;
    private String licenseNumber;
    private Double rating;
    private Integer totalRides;
    private String status;
    private String verificationStatus;
    private Double currentLatitude;
    private Double currentLongitude;
    private UserSummaryDto user;
}