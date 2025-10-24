package com.cabbooking.repository;

import com.cabbooking.entity.LocationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationLogRepository extends JpaRepository<LocationLog, Long> {

    List<LocationLog> findByDriverIdOrderByTimestampDesc(Long driverId);

    List<LocationLog> findByBookingIdOrderByTimestampDesc(Long bookingId);

    @Query("SELECT l FROM LocationLog l WHERE l.driver.id = :driverId " +
            "AND l.timestamp BETWEEN :startTime AND :endTime ORDER BY l.timestamp DESC")
    List<LocationLog> findByDriverIdAndTimeRange(
            @Param("driverId") Long driverId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT l FROM LocationLog l WHERE l.booking.id = :bookingId " +
            "AND l.timestamp BETWEEN :startTime AND :endTime ORDER BY l.timestamp DESC")
    List<LocationLog> findByBookingIdAndTimeRange(
            @Param("bookingId") Long bookingId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    @Query("SELECT l FROM LocationLog l WHERE l.driver.id = :driverId " +
            "ORDER BY l.timestamp DESC LIMIT 1")
    LocationLog findLatestLocationByDriverId(@Param("driverId") Long driverId);
}