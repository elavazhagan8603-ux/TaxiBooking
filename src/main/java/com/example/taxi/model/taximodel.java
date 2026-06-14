package com.example.taxi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_data")
public class taximodel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, password, dob;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<bookingmodel> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<roundmodel> round = new ArrayList<>();

    // ADDED: Relationship to Rental model so we can fetch user rentals
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<rentalmodel> rentals = new ArrayList<>();

    // --- GETTERS AND SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public List<bookingmodel> getTasks() {
        return tasks;
    }

    public void setTasks(List<bookingmodel> tasks) {
        this.tasks = tasks;
    }

    public List<roundmodel> getRound() {
        return round;
    }

    public void setRound(List<roundmodel> round) {
        this.round = round;
    }

    public List<rentalmodel> getRentals() {
        return rentals;
    }

    public void setRentals(List<rentalmodel> rentals) {
        this.rentals = rentals;
    }
}