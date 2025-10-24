package com.cabbooking.service;

import com.cabbooking.entity.Cab;
import com.cabbooking.entity.Driver;
import com.cabbooking.repository.CabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CabService {

    private final CabRepository cabRepository;
    private final DriverService driverService;

    public Cab createCab(Cab cab) {
        // Validate that the driver exists
        Driver driver = driverService.findById(cab.getDriver().getId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (cabRepository.findByDriverId(driver.getId()).isPresent()) {
            throw new RuntimeException("Driver already has a cab assigned");
        }

        if (cabRepository.findByLicensePlate(cab.getLicensePlate()).isPresent()) {
            throw new RuntimeException("License plate already exists");
        }

        return cabRepository.save(cab);
    }

    public Optional<Cab> findById(Long id) {
        return cabRepository.findById(id);
    }

    public Optional<Cab> findByDriverId(Long driverId) {
        return cabRepository.findByDriverId(driverId);
    }

    public Optional<Cab> findByLicensePlate(String licensePlate) {
        return cabRepository.findByLicensePlate(licensePlate);
    }

    public List<Cab> findAllCabs() {
        return cabRepository.findAll();
    }

    public List<Cab> findCabsByType(Cab.CabType cabType) {
        return cabRepository.findByCabType(cabType);
    }

    public List<Cab> findCabsByStatus(Cab.CabStatus status) {
        return cabRepository.findByStatus(status);
    }

    public List<Cab> findAvailableCabsByType(Cab.CabType cabType) {
        return cabRepository.findAvailableCabsByType(cabType);
    }

    public List<Cab> findCabsByMinimumYear(Integer year) {
        return cabRepository.findByMinimumYear(year);
    }

    public Cab updateCabStatus(Long id, Cab.CabStatus status) {
        Cab cab = cabRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cab not found"));

        cab.setStatus(status);
        return cabRepository.save(cab);
    }

    public Cab updateCab(Long id, Cab updatedCab) {
        Cab existingCab = cabRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cab not found"));

        existingCab.setLicensePlate(updatedCab.getLicensePlate());
        existingCab.setMake(updatedCab.getMake());
        existingCab.setModel(updatedCab.getModel());
        existingCab.setColor(updatedCab.getColor());
        existingCab.setYear(updatedCab.getYear());
        existingCab.setCabType(updatedCab.getCabType());
        existingCab.setSeatingCapacity(updatedCab.getSeatingCapacity());

        return cabRepository.save(existingCab);
    }

    public void deleteCab(Long id) {
        if (!cabRepository.existsById(id)) {
            throw new RuntimeException("Cab not found");
        }
        cabRepository.deleteById(id);
    }

    public Long getCabCountByStatus(Cab.CabStatus status) {
        return cabRepository.countByStatus(status);
    }

    public List<Cab> findAvailableCabs() {
        return cabRepository.findByStatus(Cab.CabStatus.AVAILABLE);
    }
}