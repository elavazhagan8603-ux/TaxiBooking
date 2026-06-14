package com.example.taxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.taxi.model.taximodel;
import java.util.Optional;

@Repository
public interface userrepository extends JpaRepository<taximodel, Long> {

    // 1. Used for login validation: Look up a user by their name
    // Optional prevents NullPointerException if the user isn't found
    Optional<taximodel> findByName(String name);

    // 2. Used for preventing duplicate registration
    boolean existsByName(String name);
}