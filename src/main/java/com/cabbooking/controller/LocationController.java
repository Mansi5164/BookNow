package com.cabbooking.controller;

import com.cabbooking.entity.LocationLog;
import com.cabbooking.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationLog> logLocation(@Valid @RequestBody LocationLog locationLog) {
        LocationLog savedLocation = locationService.logLocation(locationLog);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<LocationLog>> getDriverLocationHistory(@PathVariable Long driverId) {
        List<LocationLog> locations = locationService.getDriverLocationHistory(driverId);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<LocationLog>> getBookingLocationHistory(@PathVariable Long bookingId) {
        List<LocationLog> locations = locationService.getBookingLocationHistory(bookingId);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/driver/{driverId}/date-range")
    public ResponseEntity<List<LocationLog>> getDriverLocationHistory(
            @PathVariable Long driverId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<LocationLog> locations = locationService.getDriverLocationHistory(driverId, startTime, endTime);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/booking/{bookingId}/date-range")
    public ResponseEntity<List<LocationLog>> getBookingLocationHistory(
            @PathVariable Long bookingId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<LocationLog> locations = locationService.getBookingLocationHistory(bookingId, startTime, endTime);
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/driver/{driverId}/latest")
    public ResponseEntity<LocationLog> getLatestDriverLocation(@PathVariable Long driverId) {
        LocationLog location = locationService.getLatestDriverLocation(driverId);
        if (location != null) {
            return ResponseEntity.ok(location);
        }
        return ResponseEntity.notFound().build();
    }
}