package com.example.taxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taxi.model.drivermodel;
import java.util.List;
import java.util.Optional;

public interface driverpository extends JpaRepository<drivermodel, Long> {
    
    // 1. Used for login: Find driver by username
    // Standardizing to 'findByUsername' to match the model update
    Optional<drivermodel> findByUsername(String username);
    
    // 2. Used for Admin dashboard: Fetch by approval status
    List<drivermodel> findByApproved(boolean approved);
    
    // 3. Optional: Helpful for checking duplicates during signup
    boolean existsByUsername(String username);
}