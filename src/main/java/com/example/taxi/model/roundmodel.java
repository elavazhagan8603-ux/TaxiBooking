package com.example.taxi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roundtrip")
public class roundmodel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, email, phone, pickup, destination, depdate, deptime, retdate, rettime;
    private String status = "Pending";
    private boolean accepted = false;

    // ADDED: Persistent field for filtering in My Rides
    private String bookingType = "Round-Trip";

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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepdate() {
        return depdate;
    }

    public void setDepdate(String depdate) {
        this.depdate = depdate;
    }

    public String getDeptime() {
        return deptime;
    }

    public void setDeptime(String deptime) {
        this.deptime = deptime;
    }

    public String getRetdate() {
        return retdate;
    }

    public void setRetdate(String retdate) {
        this.retdate = retdate;
    }

    public String getRettime() {
        return rettime;
    }

    public void setRettime(String rettime) {
        this.rettime = rettime;
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