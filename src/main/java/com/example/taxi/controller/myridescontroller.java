package com.example.taxi.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.taxi.service.bookingservice;
import com.example.taxi.service.rentalservice;
import com.example.taxi.service.roundservice;
import jakarta.servlet.http.HttpSession;

@Controller
public class myridescontroller {
    @Autowired
    bookingservice bookingService;
    @Autowired
    roundservice roundService;
    @Autowired
    rentalservice rentalService;

    @GetMapping("/my-rides")
    public String showMyRides(HttpSession session, Model m) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null)
            return "redirect:/login";

        // Create ONE master list for the HTML to iterate over
        List<Object> allMyRides = new ArrayList<>();

        // Add Pending Rides
        allMyRides.addAll(bookingService.getUserRides(userId, "Pending"));
        allMyRides.addAll(roundService.getUserRoundRides(userId, "Pending"));
        allMyRides.addAll(rentalService.getUserRentalRides(userId, "Pending"));

        // Add Accepted/Booked Rides
        allMyRides.addAll(bookingService.getUserRides(userId, "Accepted"));
        allMyRides.addAll(roundService.getUserRoundRides(userId, "Accepted"));
        allMyRides.addAll(rentalService.getUserRentalRides(userId, "Booked"));

        m.addAttribute("allMyRides", allMyRides);
        return "myrides";
    }

    @PostMapping("/user/complete/{type}/{id}")
    public String completeRide(@PathVariable String type, @PathVariable Long id) {
        if ("One-Way".equals(type)) {
            bookingService.completeTrip(id);
        } else if ("Round-Trip".equals(type)) {
            roundService.completeTrip(id);
        } else if ("Rental".equals(type)) {
            rentalService.completeTrip(id);
        }
        // Redirecting correctly now
        return "redirect:/my-rides";
    }
}