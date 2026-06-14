package com.example.taxi.service;

import com.example.taxi.model.bookingmodel;
import com.example.taxi.model.drivermodel;
import com.example.taxi.repository.bookingrepository;
import com.example.taxi.repository.driverpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class bookingservice {
    
    @Autowired
    bookingrepository repo;

    @Autowired
    driverpository driverRepo;

    public void savedate(bookingmodel b) {
        repo.save(b);
    }

    // Driver sees ONLY Pending
    public List<bookingmodel> getPendingBookings() {
        return repo.findByStatus("Pending");
    }

    // Check if the driver is currently busy (has an "Accepted" ride)
    public boolean hasActiveRide(Long driverId) {
        return repo.existsByAcceptedByDriver_IdAndStatus(driverId, "Accepted");
    }

    public List<bookingmodel> getUserRides(Long userId, String status) {
        return repo.findByUser_IdAndStatus(userId, status);
    }

    public void acceptBooking(Long id, Long driverId) {
        // 1. Fetch the booking or throw custom exception
        bookingmodel b = repo.findById(id).orElseThrow(() -> new RuntimeException("Ride not found"));

        // 2. Fetch the actual driver object
        drivermodel d = driverRepo.findById(driverId).orElseThrow(() -> new RuntimeException("Driver not found"));

        // 3. Set status, full driver object, and the accepted flag
        b.setStatus("Accepted");
        b.setAccepted(true); // Sync boolean flag
        b.setAcceptedByDriver(d); 

        // 4. Save
        repo.save(b);
    }

    public void completeTrip(Long id) {
        bookingmodel b = repo.findById(id).orElseThrow(() -> new RuntimeException("Ride not found"));
        b.setStatus("Completed");
        b.setAccepted(false); // Reset status
        b.setAcceptedByDriver(null);
        repo.save(b);
    }
}