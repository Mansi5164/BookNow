package com.cabbooking.controller;

import com.cabbooking.entity.Booking;
import com.cabbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.findById(id)
                .map(booking -> ResponseEntity.ok(booking))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/number/{bookingNumber}")
    public ResponseEntity<Booking> getBookingByNumber(@PathVariable String bookingNumber) {
        return bookingService.findByBookingNumber(bookingNumber)
                .map(booking -> ResponseEntity.ok(booking))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.findBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Booking>> getBookingsByDriverId(@PathVariable Long driverId) {
        List<Booking> bookings = bookingService.findBookingsByDriverId(driverId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable Booking.BookingStatus status) {
        List<Booking> bookings = bookingService.findBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Booking>> getPendingBookings() {
        List<Booking> bookings = bookingService.findPendingBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/driver/{driverId}/active")
    public ResponseEntity<Booking> getActiveBookingByDriverId(@PathVariable Long driverId) {
        return bookingService.findActiveBookingByDriverId(driverId)
                .map(booking -> ResponseEntity.ok(booking))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{bookingId}/assign-driver/{driverId}")
    public ResponseEntity<Booking> assignDriverToBooking(
            @PathVariable Long bookingId,
            @PathVariable Long driverId) {
        try {
            Booking updatedBooking = bookingService.assignDriverToBooking(bookingId, driverId);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam Booking.BookingStatus status) {
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(bookingId, status);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{bookingId}/fare")
    public ResponseEntity<Booking> updateBookingFare(
            @PathVariable Long bookingId,
            @RequestParam BigDecimal actualFare) {
        try {
            Booking updatedBooking = bookingService.updateBookingFare(bookingId, actualFare);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{bookingId}/payment-status")
    public ResponseEntity<Booking> updatePaymentStatus(
            @PathVariable Long bookingId,
            @RequestParam Booking.PaymentStatus paymentStatus) {
        try {
            Booking updatedBooking = bookingService.updatePaymentStatus(bookingId, paymentStatus);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Booking>> getBookingsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Booking> bookings = bookingService.findBookingsByDateRange(startDate, endDate);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Booking>> getUserBookingsByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Booking> bookings = bookingService.findUserBookingsByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/driver/{driverId}/date-range")
    public ResponseEntity<List<Booking>> getDriverBookingsByDateRange(
            @PathVariable Long driverId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Booking> bookings = bookingService.findDriverBookingsByDateRange(driverId, startDate, endDate);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/stats/count")
    public ResponseEntity<Long> getBookingCountByStatus(@RequestParam Booking.BookingStatus status) {
        Long count = bookingService.getBookingCountByStatus(status);
        return ResponseEntity.ok(count);
    }
}