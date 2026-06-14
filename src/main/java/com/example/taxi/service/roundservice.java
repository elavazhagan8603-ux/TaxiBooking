package com.example.taxi.service;

import com.example.taxi.model.roundmodel;
import com.example.taxi.model.drivermodel;
import com.example.taxi.repository.roundrepository;
import com.example.taxi.repository.driverpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class roundservice {

    @Autowired
    roundrepository repo;
    
    @Autowired
    driverpository driverRepo;

    public void savedate(roundmodel r) {
        // Ensure standard defaults upon creation
        r.setStatus("Pending");
        r.setBookingType("Round-Trip");
        repo.save(r);
    }

    public List<roundmodel> getPendingRoundTrips() {
        return repo.findByStatus("Pending");
    }

    public List<roundmodel> getUserRoundRides(Long userId, String status) {
        return repo.findByUser_IdAndStatus(userId, status);
    }

    public boolean hasActiveRide(Long driverId) {
        return repo.existsByAcceptedByDriver_IdAndStatus(driverId, "Accepted");
    }

    public void acceptRoundTrip(Long id, Long driverId) {
        // Fetch the round trip
        roundmodel r = repo.findById(id).orElseThrow(() -> new RuntimeException("Round-trip not found"));
        
        // Fetch the driver object
        drivermodel d = driverRepo.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));
        
        // Update status, link driver, and set accepted flag
        r.setStatus("Accepted");
        r.setAccepted(true); 
        r.setAcceptedByDriver(d); 
        
        repo.save(r);
    }

    public void completeTrip(Long id) {
        roundmodel r = repo.findById(id).orElseThrow(() -> new RuntimeException("Round-trip not found"));
        r.setStatus("Completed");
        r.setAccepted(false); // Reset accepted flag
        r.setAcceptedByDriver(null);
        repo.save(r);
    }
}