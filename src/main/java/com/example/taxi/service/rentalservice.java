package com.example.taxi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taxi.model.rentalmodel;
import com.example.taxi.model.taximodel;
import com.example.taxi.repository.rentalrepository;
import com.example.taxi.repository.userrepository;
import java.util.List;
import java.util.ArrayList;

@Service
public class rentalservice {

    @Autowired
    rentalrepository repo;

    @Autowired
    userrepository userRepo;

    public void saveRental(rentalmodel car) {
        repo.save(car);
    }

    public rentalmodel findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    // Admin dashboard: Fetches ALL cars for the inventory view
    public List<rentalmodel> getAllCars() {
        List<rentalmodel> cars = repo.findAll();
        return (cars != null) ? cars : new ArrayList<>();
    }

    // Delete a rental car from the system entirely
    public void deleteRental(Long id) {
        repo.deleteById(id);
    }

    // ONLY get cars that are strictly "Available"
    public List<rentalmodel> getAvailableCars() {
        List<rentalmodel> cars = repo.findByStatus("Available");
        return (cars != null) ? cars : new ArrayList<>();
    }

    // Admin dashboard: see what needs approval
    public List<rentalmodel> getPendingRentals() {
        List<rentalmodel> cars = repo.findByStatus("Pending");
        return (cars != null) ? cars : new ArrayList<>();
    }

    // Admin approves: Car becomes "Booked" and links to user
    public void approveRental(Long id, Long userId) {
        rentalmodel car = repo.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
        car.setStatus("Booked");
        car.setApproved(true);
        car.setAccepted(true);

        taximodel user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        car.setUser(user);

        repo.save(car);
    }

    // User completes trip: Car resets and returns to "Available"
    public void completeTrip(Long id) {
        rentalmodel car = repo.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));

        if ("Booked".equalsIgnoreCase(car.getStatus())) {
            car.setStatus("Available");
            car.setApproved(false);
            car.setAccepted(false);
            car.setUser(null); // CRITICAL: Clear the user link
            repo.save(car);
        } else {
            throw new RuntimeException("Cannot complete trip: Car is not currently booked.");
        }
    }

    // Fetch rides by user for the "My Rides" page
    public List<rentalmodel> getUserRentalRides(Long userId, String status) {
        List<rentalmodel> cars = repo.findByUser_IdAndStatus(userId, status);
        return (cars != null) ? cars : new ArrayList<>();
    }

    // Redundant method cleanup: keep deleteRental and remove duplicate deleteById
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}