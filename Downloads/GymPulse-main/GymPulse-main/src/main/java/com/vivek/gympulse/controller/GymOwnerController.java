package com.vivek.gympulse.controller;

import com.vivek.gympulse.entity.GymOwner;
import com.vivek.gympulse.repository.GymOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vivek.gympulse.service.GymOwnerService;
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
    public GymOwner login(@RequestBody GymOwner gymOwner) {

        return gymOwnerService.login(
                gymOwner.getEmail(),
                gymOwner.getPassword()
        );
    }
}