package com.example.taxi.repository;

import com.example.taxi.model.roundmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface roundrepository extends JpaRepository<roundmodel, Long> {
    
    // 1. Fetch by status (Used for Admin dashboards)
    List<roundmodel> findByStatus(String status);

    // 2. Fetch by User ID and Status (Used for My Rides dashboard)
    List<roundmodel> findByUser_IdAndStatus(Long userId, String status);

    // 3. Check if a driver is currently busy
    // Matches: 'acceptedByDriver' entity -> 'id' field
    boolean existsByAcceptedByDriver_IdAndStatus(Long driverId, String status);

    // 4. Corrected return type and naming convention
    // Matches: 'user' entity -> 'id' field
    List<roundmodel> findByUser_IdAndAccepted(Long userId, boolean accepted);
    
    // 5. OPTIONAL: Find by Driver ID (useful for Driver dashboard)
    List<roundmodel> findByAcceptedByDriver_IdAndStatus(Long driverId, String status);
}