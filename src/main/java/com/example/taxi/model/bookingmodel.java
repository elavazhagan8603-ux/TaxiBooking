package com.example.taxi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "oneway")
public class bookingmodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String pickup;
    private String stop;
    private String date;
    private String time;

    private boolean accepted = false;
    private String status = "Pending";

    // ADDED: Persistent field for filtering
    private String bookingType = "One-Way";

    @Column(name = "accepted_by_driver_id", insertable = false, updatable = false)
    private Long acceptedByDriverId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private taximodel user;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private drivermodel acceptedByDriver;

    // --- GETTERS AND SETTERS ---

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void getTime(String time) {
        this.time = time;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAcceptedByDriverId() {
        return acceptedByDriverId;
    }

    public void setAcceptedByDriverId(Long acceptedByDriverId) {
        this.acceptedByDriverId = acceptedByDriverId;
    }

    public taximodel getUser() {
        return user;
    }

    public void setUser(taximodel user) {
        this.user = user;
    }

    public drivermodel getAcceptedByDriver() {
        return acceptedByDriver;
    }

    public void setAcceptedByDriver(drivermodel acceptedByDriver) {
        this.acceptedByDriver = acceptedByDriver;
    }
}