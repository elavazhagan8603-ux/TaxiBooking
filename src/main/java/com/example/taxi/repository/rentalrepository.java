package com.example.taxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taxi.model.rentalmodel;
import java.util.List;

public interface rentalrepository extends JpaRepository<rentalmodel, Long> {

    // 1. Used to filter cars by status (Available, Pending, Booked)
    // Essential for the Rental Home page (Available) and Admin approvals (Pending)
    List<rentalmodel> findByStatus(String status);

    // 2. Used for Admin to approve specific user requests
    List<rentalmodel> findByStatusAndApproved(String status, boolean approved);

    // 3. Used to find rentals by user and their current status (e.g., 'Booked' or
    // 'Pending')
    // Crucial for the My Rides dashboard to filter active vs pending
    List<rentalmodel> findByUser_IdAndStatus(Long userId, String status);

    // 4. Used to find rentals by user and accepted status (boolean)
    List<rentalmodel> findByUser_IdAndAccepted(Long userId, boolean accepted);

    // 5. General check for accepted status across all users
    List<rentalmodel> findByAccepted(boolean accepted);

    // 6. Get ALL records for a user, regardless of status
    // Use this to debug if a user claims a booking is missing
    List<rentalmodel> findByUser_Id(Long userId);

    // 7. Count rentals by status
    // Perfect for dashboard widgets (e.g., "Pending Approvals: 5")
    long countByStatus(String status);
}