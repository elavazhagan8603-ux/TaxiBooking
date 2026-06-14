package com.example.taxi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taxi.model.adminmodel;

@Repository
public interface adminrepository extends JpaRepository<adminmodel, Long> {
    adminmodel findByUsername(String username);
}