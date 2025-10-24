package com.cabbooking.repository;

import com.cabbooking.entity.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CabRepository extends JpaRepository<Cab, Long> {

    Optional<Cab> findByDriverId(Long driverId);

    Optional<Cab> findByLicensePlate(String licensePlate);

    List<Cab> findByCabType(Cab.CabType cabType);

    List<Cab> findByStatus(Cab.CabStatus status);

    @Query("SELECT c FROM Cab c WHERE c.cabType = :cabType AND c.status = :status")
    List<Cab> findByCabTypeAndStatus(
            @Param("cabType") Cab.CabType cabType,
            @Param("status") Cab.CabStatus status);

    @Query("SELECT c FROM Cab c JOIN c.driver d WHERE d.status = 'ONLINE' " +
            "AND c.status = 'AVAILABLE' AND d.verificationStatus = 'VERIFIED' " +
            "AND c.cabType = :cabType")
    List<Cab> findAvailableCabsByType(@Param("cabType") Cab.CabType cabType);

    @Query("SELECT c FROM Cab c WHERE c.year >= :year")
    List<Cab> findByMinimumYear(@Param("year") Integer year);

    @Query("SELECT COUNT(c) FROM Cab c WHERE c.status = :status")
    Long countByStatus(@Param("status") Cab.CabStatus status);
}