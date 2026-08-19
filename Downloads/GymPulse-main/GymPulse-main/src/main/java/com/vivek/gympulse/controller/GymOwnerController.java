package com.vivek.gympulse.controller;

import com.vivek.gympulse.entity.GymOwner;
import com.vivek.gympulse.service.GymOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vivek.gympulse.dto.LoginResponse;
import com.vivek.gympulse.dto.ChangePasswordRequest;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/owner")
public class GymOwnerController {

    @Autowired
    private GymOwnerService gymOwnerService;

    @PostMapping("/register")
    public GymOwner register(@RequestBody GymOwner gymOwner) {
        return gymOwnerService.register(gymOwner);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody GymOwner gymOwner) {
        return gymOwnerService.login(
                gymOwner.getEmail(),
                gymOwner.getPassword()
        );
    }
    @PutMapping("/update/{id}")
    public GymOwner updateProfile(
            @PathVariable Long id,
            @RequestBody GymOwner gymOwner
    ) {
        return gymOwnerService.updateProfile(id, gymOwner);
    }
    @PutMapping("/change-password/{id}")
    public String changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request
    ) {

        gymOwnerService.changePassword(id, request);

        return "Password Changed Successfully";
    }
}