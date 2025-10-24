package com.cabbooking.controller;

import com.cabbooking.entity.Cab;
import com.cabbooking.service.CabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.cabbooking.util.ApplicationConstants.API_CABS_BASE_PATH;
import static com.cabbooking.util.ApplicationConstants.HTTP_CREATED;

@RestController
@RequestMapping(API_CABS_BASE_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CabController {

    private final CabService cabService;

    @PostMapping
    public ResponseEntity<Cab> createCab(@Valid @RequestBody Cab cab) {
        try {
            Cab createdCab = cabService.createCab(cab);
            return new ResponseEntity<>(createdCab, HttpStatus.valueOf(HTTP_CREATED));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cab> getCabById(@PathVariable Long id) {
        return cabService.findById(id)
                .map(cab -> ResponseEntity.ok(cab))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Cab> getCabByDriverId(@PathVariable Long driverId) {
        return cabService.findByDriverId(driverId)
                .map(cab -> ResponseEntity.ok(cab))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/license/{licensePlate}")
    public ResponseEntity<Cab> getCabByLicensePlate(@PathVariable String licensePlate) {
        return cabService.findByLicensePlate(licensePlate)
                .map(cab -> ResponseEntity.ok(cab))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Cab>> getAllCabs() {
        List<Cab> cabs = cabService.findAllCabs();
        return ResponseEntity.ok(cabs);
    }

    @GetMapping("/type/{cabType}")
    public ResponseEntity<List<Cab>> getCabsByType(@PathVariable Cab.CabType cabType) {
        List<Cab> cabs = cabService.findCabsByType(cabType);
        return ResponseEntity.ok(cabs);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Cab>> getCabsByStatus(@PathVariable Cab.CabStatus status) {
        List<Cab> cabs = cabService.findCabsByStatus(status);
        return ResponseEntity.ok(cabs);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Cab>> getAvailableCabs() {
        List<Cab> cabs = cabService.findAvailableCabs();
        return ResponseEntity.ok(cabs);
    }

    @GetMapping("/available/type/{cabType}")
    public ResponseEntity<List<Cab>> getAvailableCabsByType(@PathVariable Cab.CabType cabType) {
        List<Cab> cabs = cabService.findAvailableCabsByType(cabType);
        return ResponseEntity.ok(cabs);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Cab>> getCabsByMinimumYear(@PathVariable Integer year) {
        List<Cab> cabs = cabService.findCabsByMinimumYear(year);
        return ResponseEntity.ok(cabs);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Cab> updateCabStatus(@PathVariable Long id, @RequestParam Cab.CabStatus status) {
        try {
            Cab updatedCab = cabService.updateCabStatus(id, status);
            return ResponseEntity.ok(updatedCab);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cab> updateCab(@PathVariable Long id, @Valid @RequestBody Cab cab) {
        try {
            Cab updatedCab = cabService.updateCab(id, cab);
            return ResponseEntity.ok(updatedCab);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCab(@PathVariable Long id) {
        try {
            cabService.deleteCab(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stats/count")
    public ResponseEntity<Long> getCabCountByStatus(@RequestParam Cab.CabStatus status) {
        Long count = cabService.getCabCountByStatus(status);
        return ResponseEntity.ok(count);
    }
}