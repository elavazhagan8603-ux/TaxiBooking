package com.example.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.taxi.model.rentalmodel;
import com.example.taxi.repository.*;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class usercontroller {

    @Autowired
    bookingrepository onewayRepo;
    @Autowired
    roundrepository roundRepo;
    @Autowired
    rentalrepository rentalRepo;

    @GetMapping("/my-rides")
    public String showMyRides(Model m, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null)
            return "redirect:/login";

        // DEBUG: Console logging to verify database state
        List<rentalmodel> allRentals = rentalRepo.findAll();
        System.out.println("DEBUG: Total rentals in database: " + allRentals.size());
        for (rentalmodel r : allRentals) {
            String uid = (r.getUser() != null) ? String.valueOf(r.getUser().getId()) : "null";
            System.out.println(
                    "DEBUG: Rental ID " + r.getId() + " | UserID in DB: " + uid + " | Status: " + r.getStatus());
        }

        // 1. Pending: Uses status "Pending"
        List<Object> pending = new ArrayList<>();
        pending.addAll(onewayRepo.findByUser_IdAndStatus(userId, "Pending"));
        pending.addAll(roundRepo.findByUser_IdAndStatus(userId, "Pending"));
        pending.addAll(rentalRepo.findByUser_IdAndStatus(userId, "Pending"));
        m.addAttribute("pendingRides", pending);

        // 2. Active/Accepted:
        // Standard rides use 'Accepted', Rentals use 'Booked' (Admin approved)
        List<Object> accepted = new ArrayList<>();
        accepted.addAll(onewayRepo.findByUser_IdAndStatus(userId, "Accepted"));
        accepted.addAll(roundRepo.findByUser_IdAndStatus(userId, "Accepted"));

        // Fetch rentals where the logged-in user is the one linked AND status is
        // "Booked"
        accepted.addAll(rentalRepo.findByUser_IdAndStatus(userId, "Booked"));

        m.addAttribute("acceptedRides", accepted);

        return "myrides";
    }
}