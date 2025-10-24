package com.cabbooking.repository;

import com.cabbooking.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByUserId(Long userId);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    List<Driver> findByStatus(Driver.DriverStatus status);

    List<Driver> findByVerificationStatus(Driver.VerificationStatus verificationStatus);

    @Query("SELECT d FROM Driver d WHERE d.status = :status AND d.verificationStatus = :verificationStatus")
    List<Driver> findByStatusAndVerificationStatus(
            @Param("status") Driver.DriverStatus status,
            @Param("verificationStatus") Driver.VerificationStatus verificationStatus);

    @Query("SELECT d FROM Driver d WHERE d.status = 'ONLINE' AND d.verificationStatus = 'VERIFIED' " +
            "AND d.currentLatitude IS NOT NULL AND d.currentLongitude IS NOT NULL " +
            "AND (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(d.currentLatitude)) * " +
            "COS(RADIANS(d.currentLongitude) - RADIANS(:longitude)) + " +
            "SIN(RADIANS(:latitude)) * SIN(RADIANS(d.currentLatitude)))) <= :radiusKm " +
            "ORDER BY (6371 * ACOS(COS(RADIANS(:latitude)) * COS(RADIANS(d.currentLatitude)) * " +
            "COS(RADIANS(d.currentLongitude) - RADIANS(:longitude)) + " +
            "SIN(RADIANS(:latitude)) * SIN(RADIANS(d.currentLatitude))))")
    List<Driver> findNearbyAvailableDrivers(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radiusKm") Double radiusKm);

    @Query("SELECT d FROM Driver d WHERE d.rating >= :minRating ORDER BY d.rating DESC")
    List<Driver> findByMinimumRating(@Param("minRating") Double minRating);

    @Query("SELECT AVG(d.rating) FROM Driver d WHERE d.verificationStatus = 'VERIFIED'")
    Double getAverageRating();
}