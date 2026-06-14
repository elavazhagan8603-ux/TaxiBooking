package com.example.taxi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rentalcars")
public class rentalmodel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String carName;
    private String carType;
    private String pricePerDay;
    private int days;
    private double totalPrice;
    private String imageUrl;

    // This field manages visibility: "Available", "Pending", "Booked"
    private String status = "Available";

    // Standard flags
    private boolean accepted = false;
    private boolean approved = false;

    // Link only to the User; Drivers are removed for Rental logic
    @ManyToOne
    @JoinColumn(name = "user_id")
    private taximodel user;

    // --- GETTERS AND SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(String pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public taximodel getUser() {
        return user;
    }

    public void setUser(taximodel user) {
        this.user = user;
    }

    // Returning "Rental" ensures compatibility with your myridescontroller
    // filtering
    public String getBookingType() {
        return "Rental";
    }
}