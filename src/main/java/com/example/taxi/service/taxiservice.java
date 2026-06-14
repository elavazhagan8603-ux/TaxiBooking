package com.example.taxi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taxi.model.taximodel;
import com.example.taxi.repository.taxirepository;
import java.util.Optional;

@Service
public class taxiservice {
    @Autowired
    taxirepository taxirepo;

    public void savedate(taximodel user) {
        taxirepo.save(user);
    }

    public taximodel displayUser(Long id) {
        return taxirepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public taximodel checkData(String name, String password) {
        // Using Optional handles the "User not found" case gracefully
        Optional<taximodel> userOpt = taxirepo.findByName(name);

        if (userOpt.isPresent()) {
            taximodel user = userOpt.get();
            // Validate password
            if (user.getPassword().equals(password)) {
                return user; // Login successful
            }
        }
        return null; // Either user not found or password incorrect
    }
}