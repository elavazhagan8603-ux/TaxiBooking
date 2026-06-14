package com.example.taxi.repository;

import com.example.taxi.model.bookingmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface bookingrepository extends JpaRepository<bookingmodel, Long> {

    // 1. Fetch by status (Used for Admin dashboards)
    List<bookingmodel> findByStatus(String status);

    // 2. Fetch by User ID and Status (Used for My Rides dashboard)
    // Matches: 'user' entity -> 'id' field
    List<bookingmodel> findByUser_IdAndStatus(Long userId, String status);

    // 3. Check if a driver is currently busy
    // Matches: 'acceptedByDriver' entity -> 'id' field
    boolean existsByAcceptedByDriver_IdAndStatus(Long driverId, String status);

    // 4. Fetch by User ID and Accepted boolean
    List<bookingmodel> findByUser_IdAndAccepted(Long userId, boolean accepted);
    
    // 5. OPTIONAL: Find by Driver ID (useful for Driver dashboard)
    List<bookingmodel> findByAcceptedByDriver_IdAndStatus(Long driverId, String status);
}