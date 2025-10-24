package com.cabbooking.service;

import com.cabbooking.entity.*;
import com.cabbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.cabbooking.util.ApplicationConstants.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final DriverService driverService;
    private final CabService cabService;

    public Booking createBooking(Booking booking) {
        // Validate user exists
        userService.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        // Generate unique booking number
        booking.setBookingNumber(BOOKING_NUMBER_PREFIX + UUID.randomUUID().toString().substring(0, BOOKING_NUMBER_LENGTH).toUpperCase());

        // Calculate estimated fare and duration (simplified calculation)
        BigDecimal estimatedFare = calculateEstimatedFare(
                booking.getPickupLatitude(), booking.getPickupLongitude(),
                booking.getDropoffLatitude(), booking.getDropoffLongitude(),
                booking.getRequestedCabType());

        booking.setEstimatedFare(estimatedFare);
        booking.setDistance(calculateDistance(
                booking.getPickupLatitude(), booking.getPickupLongitude(),
                booking.getDropoffLatitude(), booking.getDropoffLongitude()));

        booking.setEstimatedDuration(calculateEstimatedDuration(booking.getDistance()));

        return bookingRepository.save(booking);
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public Optional<Booking> findByBookingNumber(String bookingNumber) {
        return bookingRepository.findByBookingNumber(bookingNumber);
    }

    public List<Booking> findBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> findBookingsByDriverId(Long driverId) {
        return bookingRepository.findByDriverId(driverId);
    }

    public List<Booking> findBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    public List<Booking> findPendingBookings() {
        return bookingRepository.findPendingBookings(LocalDateTime.now());
    }

    public Optional<Booking> findActiveBookingByDriverId(Long driverId) {
        return bookingRepository.findActiveBookingByDriverId(driverId);
    }

    public Booking assignDriverToBooking(Long bookingId, Long driverId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException(BOOKING_NOT_FOUND));

        Driver driver = driverService.findById(driverId)
                .orElseThrow(() -> new RuntimeException(DRIVER_NOT_FOUND));

        if (booking.getStatus() != Booking.BookingStatus.REQUESTED) {
            throw new RuntimeException(BOOKING_NOT_IN_REQUESTED_STATUS);
        }

        if (driver.getStatus() != Driver.DriverStatus.ONLINE) {
            throw new RuntimeException(DRIVER_IS_NOT_ONLINE);
        }

        // Check if driver already has an active booking
        if (findActiveBookingByDriverId(driverId).isPresent()) {
            throw new RuntimeException(DRIVER_HAS_ACTIVE_BOOKING);
        }

        // Get driver's cab
        Cab cab = cabService.findByDriverId(driverId)
                .orElseThrow(() -> new RuntimeException(DRIVER_DOES_NOT_HAVE_CAB));

        booking.setDriver(driver);
        booking.setCab(cab);
        booking.setStatus(Booking.BookingStatus.ACCEPTED);
        booking.setAcceptedTime(LocalDateTime.now());

        // Update driver and cab status
        driverService.updateDriverStatus(driverId, Driver.DriverStatus.BUSY);
        cabService.updateCabStatus(cab.getId(), Cab.CabStatus.BUSY);

        return bookingRepository.save(booking);
    }

    public Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException(BOOKING_NOT_FOUND));

        Booking.BookingStatus currentStatus = booking.getStatus();

        // Validate status transition
        validateStatusTransition(currentStatus, status);

        booking.setStatus(status);

        // Update timestamps based on status
        switch (status) {
            case REQUESTED:
                // No specific action needed
                break;
            case ACCEPTED:
                // No specific action needed
                break;
            case DRIVER_ASSIGNED:
                break;
            case DRIVER_ARRIVED:
                break;
            case IN_PROGRESS:
                booking.setStartTime(LocalDateTime.now());
                break;
            case COMPLETED:
                booking.setEndTime(LocalDateTime.now());
                if (booking.getStartTime() != null) {
                    booking.setActualDuration(
                            (int) java.time.Duration.between(booking.getStartTime(), booking.getEndTime()).toMinutes());
                }
                // Update driver and cab status back to available
                if (booking.getDriver() != null) {
                    driverService.updateDriverStatus(booking.getDriver().getId(), Driver.DriverStatus.ONLINE);
                    if (booking.getCab() != null) {
                        cabService.updateCabStatus(booking.getCab().getId(), Cab.CabStatus.AVAILABLE);
                    }
                }
                break;
            case CANCELLED:
                // Update driver and cab status back to available
                if (booking.getDriver() != null) {
                    driverService.updateDriverStatus(booking.getDriver().getId(), Driver.DriverStatus.ONLINE);
                    if (booking.getCab() != null) {
                        cabService.updateCabStatus(booking.getCab().getId(), Cab.CabStatus.AVAILABLE);
                    }
                }
                break;
        }

        return bookingRepository.save(booking);
    }

    public Booking updateBookingFare(Long bookingId, BigDecimal actualFare) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException(BOOKING_NOT_FOUND));

        booking.setActualFare(actualFare);
        return bookingRepository.save(booking);
    }

    public Booking updatePaymentStatus(Long bookingId, Booking.PaymentStatus paymentStatus) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException(BOOKING_NOT_FOUND));

        booking.setPaymentStatus(paymentStatus);
        return bookingRepository.save(booking);
    }

    public List<Booking> findBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByDateRange(startDate, endDate);
    }

    public List<Booking> findUserBookingsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByUserIdAndDateRange(userId, startDate, endDate);
    }

    public List<Booking> findDriverBookingsByDateRange(Long driverId, LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByDriverIdAndDateRange(driverId, startDate, endDate);
    }

    public Long getBookingCountByStatus(Booking.BookingStatus status) {
        return bookingRepository.countByStatus(status);
    }

    private void validateStatusTransition(Booking.BookingStatus from, Booking.BookingStatus to) {
        // Define valid status transitions
        boolean isValidTransition = switch (from) {
            case REQUESTED -> to == Booking.BookingStatus.ACCEPTED || to == Booking.BookingStatus.CANCELLED;
            case ACCEPTED -> to == Booking.BookingStatus.DRIVER_ASSIGNED || to == Booking.BookingStatus.CANCELLED;
            case DRIVER_ASSIGNED -> to == Booking.BookingStatus.DRIVER_ARRIVED || to == Booking.BookingStatus.CANCELLED;
            case DRIVER_ARRIVED -> to == Booking.BookingStatus.IN_PROGRESS || to == Booking.BookingStatus.CANCELLED;
            case IN_PROGRESS -> to == Booking.BookingStatus.COMPLETED;
            case COMPLETED, CANCELLED -> false; // Terminal states
        };

        if (!isValidTransition) {
            String message = String.format(
                    INVALID_STATUS_TRANSITION_MSG,
                    from,
                    to
            );

            throw new RuntimeException(message);
        }
    }

    private BigDecimal calculateEstimatedFare(Double pickupLat, Double pickupLng,
            Double dropoffLat, Double dropoffLng,
            Booking.CabType cabType) {
        BigDecimal distance = calculateDistance(pickupLat, pickupLng, dropoffLat, dropoffLng);
        BigDecimal perKmRate = getPerKmRate(cabType);

        return BASE_FARE.add(distance.multiply(perKmRate));
    }

    private BigDecimal getPerKmRate(Booking.CabType cabType) {
        return switch (cabType) {
                case HATCHBACK -> HATCHBACK_FARE;
                case SEDAN -> SEDAN_FARE;
                case SUV -> SUV_FARE;
                case LUXURY -> LUXURY_FARE;
        };
    }

    private BigDecimal calculateDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
        // Haversine formula for distance calculation


        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return BigDecimal.valueOf(distance);
    }

    private Integer calculateEstimatedDuration(BigDecimal distance) {
        // Assuming average speed of 30 km/h in city traffic
        double durationHours = distance.doubleValue() / DEFAULT_AVG_SPEED_KMH;
        return (int) (durationHours * MINUTES_IN_HOUR); // Convert to minutes
    }
}