package com.cabbooking.controller;

import com.cabbooking.entity.Driver;
import com.cabbooking.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody Driver driver) {
        try {
            Driver createdDriver = driverService.createDriver(driver);
            return new ResponseEntity<>(createdDriver, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return driverService.findById(id)
                .map(driver -> ResponseEntity.ok(driver))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Driver> getDriverByUserId(@PathVariable Long userId) {
        return driverService.findByUserId(userId)
                .map(driver -> ResponseEntity.ok(driver))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/license/{licenseNumber}")
    public ResponseEntity<Driver> getDriverByLicenseNumber(@PathVariable String licenseNumber) {
        return driverService.findByLicenseNumber(licenseNumber)
                .map(driver -> ResponseEntity.ok(driver))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.findAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Driver>> getDriversByStatus(@PathVariable Driver.DriverStatus status) {
        List<Driver> drivers = driverService.findDriversByStatus(status);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/verification/{verificationStatus}")
    public ResponseEntity<List<Driver>> getDriversByVerificationStatus(
            @PathVariable Driver.VerificationStatus verificationStatus) {
        List<Driver> drivers = driverService.findDriversByVerificationStatus(verificationStatus);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers() {
        List<Driver> drivers = driverService.findAvailableDrivers();
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Driver>> getNearbyDrivers(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5.0") Double radiusKm) {
        List<Driver> drivers = driverService.findNearbyDrivers(latitude, longitude, radiusKm);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<Driver>> getTopRatedDrivers(@RequestParam(defaultValue = "4.0") Double minRating) {
        List<Driver> drivers = driverService.findTopRatedDrivers(minRating);
        return ResponseEntity.ok(drivers);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Driver> updateDriverStatus(@PathVariable Long id, @RequestParam Driver.DriverStatus status) {
        try {
            Driver updatedDriver = driverService.updateDriverStatus(id, status);
            return ResponseEntity.ok(updatedDriver);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<Driver> updateDriverLocation(
            @PathVariable Long id,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        try {
            Driver updatedDriver = driverService.updateDriverLocation(id, latitude, longitude);
            return ResponseEntity.ok(updatedDriver);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/verification")
    public ResponseEntity<Driver> updateVerificationStatus(
            @PathVariable Long id,
            @RequestParam Driver.VerificationStatus verificationStatus) {
        try {
            Driver updatedDriver = driverService.updateVerificationStatus(id, verificationStatus);
            return ResponseEntity.ok(updatedDriver);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<Driver> updateDriverRating(@PathVariable Long id, @RequestParam Double rating) {
        try {
            Driver updatedDriver = driverService.updateDriverRating(id, rating);
            return ResponseEntity.ok(updatedDriver);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @Valid @RequestBody Driver driver) {
        try {
            Driver updatedDriver = driverService.updateDriver(id, driver);
            return ResponseEntity.ok(updatedDriver);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        try {
            driverService.deleteDriver(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stats/average-rating")
    public ResponseEntity<Double> getAverageDriverRating() {
        Double averageRating = driverService.getAverageDriverRating();
        return ResponseEntity.ok(averageRating);
    }
}