package com.cabbooking.service;

import com.cabbooking.entity.LocationLog;
import com.cabbooking.repository.LocationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationService {

    private final LocationLogRepository locationLogRepository;

    public LocationLog logLocation(LocationLog locationLog) {
        return locationLogRepository.save(locationLog);
    }

    public List<LocationLog> getDriverLocationHistory(Long driverId) {
        return locationLogRepository.findByDriverIdOrderByTimestampDesc(driverId);
    }

    public List<LocationLog> getBookingLocationHistory(Long bookingId) {
        return locationLogRepository.findByBookingIdOrderByTimestampDesc(bookingId);
    }

    public List<LocationLog> getDriverLocationHistory(Long driverId, LocalDateTime startTime, LocalDateTime endTime) {
        return locationLogRepository.findByDriverIdAndTimeRange(driverId, startTime, endTime);
    }

    public List<LocationLog> getBookingLocationHistory(Long bookingId, LocalDateTime startTime, LocalDateTime endTime) {
        return locationLogRepository.findByBookingIdAndTimeRange(bookingId, startTime, endTime);
    }

    public LocationLog getLatestDriverLocation(Long driverId) {
        return locationLogRepository.findLatestLocationByDriverId(driverId);
    }
}