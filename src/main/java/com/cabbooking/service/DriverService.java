package com.cabbooking.service;

import com.cabbooking.entity.Driver;
import com.cabbooking.entity.User;
import com.cabbooking.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.cabbooking.util.ApplicationConstants.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserService userService;

    public Driver createDriver(Driver driver) {
        // Validate that the user exists and has driver role
        User user = userService.findById(driver.getUser().getId())
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        if (user.getRole() != User.UserRole.DRIVER) {
            throw new RuntimeException(USER_MUST_HAVE_DRIVER_ROLE);
        }

        if (driverRepository.findByLicenseNumber(driver.getLicenseNumber()).isPresent()) {
            throw new RuntimeException(LICENSE_NUMBER_ALREADY_EXISTS);
        }

        return driverRepository.save(driver);
    }

    public Optional<Driver> findById(Long id) {
        return driverRepository.findById(id);
    }

    public Optional<Driver> findByUserId(Long userId) {
        return driverRepository.findByUserId(userId);
    }

    public Optional<Driver> findByLicenseNumber(String licenseNumber) {
        return driverRepository.findByLicenseNumber(licenseNumber);
    }

    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }

    public List<Driver> findDriversByStatus(Driver.DriverStatus status) {
        return driverRepository.findByStatus(status);
    }

    public List<Driver> findDriversByVerificationStatus(Driver.VerificationStatus verificationStatus) {
        return driverRepository.findByVerificationStatus(verificationStatus);
    }

    public List<Driver> findAvailableDrivers() {
        return driverRepository.findByStatusAndVerificationStatus(
                Driver.DriverStatus.ONLINE,
                Driver.VerificationStatus.VERIFIED);
    }

    public List<Driver> findNearbyDrivers(Double latitude, Double longitude, Double radiusKm) {
        return driverRepository.findNearbyAvailableDrivers(latitude, longitude, radiusKm);
    }

    public List<Driver> findTopRatedDrivers(Double minRating) {
        return driverRepository.findByMinimumRating(minRating);
    }

    public Driver updateDriverStatus(Long id, Driver.DriverStatus status) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(DRIVER_NOT_FOUND));

        driver.setStatus(status);
        return driverRepository.save(driver);
    }

    public Driver updateDriverLocation(Long id, Double latitude, Double longitude) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(DRIVER_NOT_FOUND));

        driver.setCurrentLatitude(latitude);
        driver.setCurrentLongitude(longitude);
        return driverRepository.save(driver);
    }

    public Driver updateVerificationStatus(Long id, Driver.VerificationStatus verificationStatus) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(DRIVER_NOT_FOUND));

        driver.setVerificationStatus(verificationStatus);
        return driverRepository.save(driver);
    }

    public Driver updateDriverRating(Long id, Double newRating) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(DRIVER_NOT_FOUND));

        // Calculate new average rating (simplified - in real app, you'd track
        // individual ratings)
        int totalRides = driver.getTotalRides();
        double currentRating = driver.getRating();

        double updatedRating = ((currentRating * totalRides) + newRating) / (totalRides + SINGLE_RIDE_INCREMENT);

        driver.setRating(updatedRating);
        driver.setTotalRides(totalRides + SINGLE_RIDE_INCREMENT);

        return driverRepository.save(driver);
    }

    public Driver updateDriver(Long id, Driver updatedDriver) {
        Driver existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(DRIVER_NOT_FOUND));

        existingDriver.setLicenseNumber(updatedDriver.getLicenseNumber());
        existingDriver.setLicenseExpiryDate(updatedDriver.getLicenseExpiryDate());

        return driverRepository.save(existingDriver);
    }

    public void deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new RuntimeException(DRIVER_NOT_FOUND);
        }
        driverRepository.deleteById(id);
    }

    public Double getAverageDriverRating() {
        return driverRepository.getAverageRating();
    }
}