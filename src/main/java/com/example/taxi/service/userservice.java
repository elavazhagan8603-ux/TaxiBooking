package com.example.taxi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taxi.model.taximodel;
import com.example.taxi.repository.userrepository;

@Service
public class userservice {

    @Autowired
    userrepository userRepo;

    // Used to find a user by ID (Essential for linking bookings/rentals)
    public taximodel findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Used to save or update user data
    public void saveUser(taximodel user) {
        userRepo.save(user);
    }

    // Used to check if a user exists (Optional helper)
    public boolean existsById(Long id) {
        return userRepo.existsById(id);
    }
}