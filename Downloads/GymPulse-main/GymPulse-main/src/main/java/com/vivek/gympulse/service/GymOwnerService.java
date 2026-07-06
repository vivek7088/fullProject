package com.vivek.gympulse.service;

import com.vivek.gympulse.entity.GymOwner;
import com.vivek.gympulse.repository.GymOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import com.vivek.gympulse.security.JwtService;
import com.vivek.gympulse.dto.LoginResponse;

@Service
public class GymOwnerService {

    @Autowired
    private GymOwnerRepository gymOwnerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public GymOwner register(GymOwner gymOwner) {

        gymOwner.setPassword(passwordEncoder.encode(gymOwner.getPassword()));

        gymOwner.setRole("GYM_OWNER");
        gymOwner.setPlan("TRIAL");
        gymOwner.setSubscriptionExpiry(LocalDate.now().plusDays(7));
        gymOwner.setActive(true);

        return gymOwnerRepository.save(gymOwner);
    }

    public LoginResponse login(String email, String password) {

        GymOwner owner = gymOwnerRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (!passwordEncoder.matches(password, owner.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        if (!owner.getActive()) {
            throw new RuntimeException("Your subscription is inactive.");
        }

        if (owner.getSubscriptionExpiry().isBefore(LocalDate.now())) {
            throw new RuntimeException("Your subscription has expired.");
        }

        String token = jwtService.generateToken(owner.getEmail());

        return new LoginResponse(token, owner);
    }
}