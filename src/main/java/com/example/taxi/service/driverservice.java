package com.example.taxi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.taxi.model.drivermodel;
import com.example.taxi.repository.driverpository;
import java.util.List;
import java.util.Optional;

@Service
public class driverservice {
    @Autowired
    driverpository repo;

    public void register(drivermodel driver) { 
        // Default to not approved upon registration
        driver.setApproved(false);
        repo.save(driver); 
    }
    
    public List<drivermodel> getPending() { 
        return repo.findByApproved(false); 
    }
    
    public void approve(Long id) {
        drivermodel d = repo.findById(id).orElseThrow(() -> new RuntimeException("Driver not found"));
        d.setApproved(true);
        repo.save(d);
    }

    public drivermodel login(String username, String password) {
        // Use Optional to handle the database lookup safely
        Optional<drivermodel> driverOpt = repo.findByUsername(username);
        
        if (driverOpt.isPresent()) {
            drivermodel d = driverOpt.get();
            // Validate password and approval status
            if (d.getPassword().equals(password) && d.isApproved()) {
                return d;
            }
        }
        return null;
    }

    public drivermodel findById(Long id) {
        return repo.findById(id).orElse(null);
    }
}