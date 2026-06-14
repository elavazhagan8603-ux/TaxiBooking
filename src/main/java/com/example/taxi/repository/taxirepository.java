package com.example.taxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.taxi.model.taximodel;
import java.util.Optional;

@Repository
public interface taxirepository extends JpaRepository<taximodel, Long> {

    // 1. Find user by name (Used for login or profile lookups)
    // Using Optional is best practice to avoid NullPointerExceptions
    Optional<taximodel> findByName(String name);

    // 2. Check if a user already exists (Used during signup)
    boolean existsByName(String name);
}