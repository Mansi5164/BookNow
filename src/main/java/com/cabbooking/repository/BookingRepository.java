package com.cabbooking.repository;

import com.cabbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingNumber(String bookingNumber);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByDriverId(Long driverId);

    List<Booking> findByStatus(Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.status = :status")
    List<Booking> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.driver.id = :driverId AND b.status = :status")
    List<Booking> findByDriverIdAndStatus(@Param("driverId") Long driverId,
            @Param("status") Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.createdAt BETWEEN :startDate AND :endDate")
    List<Booking> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId " +
            "AND b.createdAt BETWEEN :startDate AND :endDate ORDER BY b.createdAt DESC")
    List<Booking> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.driver.id = :driverId " +
            "AND b.createdAt BETWEEN :startDate AND :endDate ORDER BY b.createdAt DESC")
    List<Booking> findByDriverIdAndDateRange(
            @Param("driverId") Long driverId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    Long countByStatus(@Param("status") Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.status IN ('REQUESTED', 'ACCEPTED') " +
            "AND b.requestedTime <= :currentTime ORDER BY b.requestedTime ASC")
    List<Booking> findPendingBookings(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT b FROM Booking b WHERE b.driver.id = :driverId " +
            "AND b.status IN ('ACCEPTED', 'DRIVER_ASSIGNED', 'DRIVER_ARRIVED', 'IN_PROGRESS')")
    Optional<Booking> findActiveBookingByDriverId(@Param("driverId") Long driverId);
}