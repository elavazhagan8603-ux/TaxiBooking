package com.example.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.taxi.service.bookingservice;
import com.example.taxi.service.roundservice;
import jakarta.servlet.http.HttpSession;

@Controller
public class driverhomecontroller {

    @Autowired
    bookingservice bookingService;

    @Autowired
    roundservice roundService;

    @GetMapping("/driverhome")
    public String viewTrips(Model m, HttpSession session) {
        // Use "driver_id" to match the session key set in drivercontroller
        Long driverId = (Long) session.getAttribute("driver_id");

        if (driverId == null) {
            return "redirect:/driverlogin";
        }

        // Logic: Check if the driver is currently busy
        boolean isBusy = bookingService.hasActiveRide(driverId) || roundService.hasActiveRide(driverId);

        // Load pending trips for the dashboard
        m.addAttribute("bookings", bookingService.getPendingBookings());
        m.addAttribute("roundTrips", roundService.getPendingRoundTrips());

        // Pass the busy status to enable/disable buttons in driverhome.html
        m.addAttribute("isBusy", isBusy);

        return "driverhome";
    }

    // Accept One-Way
    @PostMapping("/driver/accept-oneway/{id}")
    public String acceptOneWay(@PathVariable Long id, HttpSession session) {
        Long driverId = (Long) session.getAttribute("driver_id");
        if (driverId != null) {
            bookingService.acceptBooking(id, driverId);
        }
        return "redirect:/driverhome";
    }

    // Accept Round-Trip
    @PostMapping("/driver/accept-round/{id}")
    public String acceptRound(@PathVariable Long id, HttpSession session) {
        Long driverId = (Long) session.getAttribute("driver_id");
        if (driverId != null) {
            roundService.acceptRoundTrip(id, driverId);
        }
        return "redirect:/driverhome";
    }

    // Complete One-Way
    @PostMapping("/driver/complete-oneway/{id}")
    @ResponseBody
    public ResponseEntity<String> completeOneWay(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("driver_id") == null) return ResponseEntity.status(401).build();
        bookingService.completeTrip(id);
        return ResponseEntity.ok("Trip completed successfully");
    }

    // Complete Round-Trip
    @PostMapping("/driver/complete-round/{id}")
    @ResponseBody
    public ResponseEntity<String> completeRound(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("driver_id") == null) return ResponseEntity.status(401).build();
        roundService.completeTrip(id);
        return ResponseEntity.ok("Trip completed successfully");
    }
}