package com.vivek.gympulse.service;

import com.vivek.gympulse.entity.GymOwner;
import com.vivek.gympulse.repository.GymOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import com.vivek.gympulse.security.JwtService;
import com.vivek.gympulse.dto.LoginResponse;
import com.vivek.gympulse.dto.ChangePasswordRequest;
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
    public void changePassword(Long ownerId, ChangePasswordRequest request) {

        GymOwner owner = gymOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Gym Owner Not Found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), owner.getPassword())) {
            throw new RuntimeException("Current Password is Incorrect");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New Password and Confirm Password do not match");
        }

        owner.setPassword(passwordEncoder.encode(request.getNewPassword()));

        gymOwnerRepository.save(owner);
    }
    public GymOwner updateProfile(Long id, GymOwner updatedOwner) {

        GymOwner owner = gymOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gym Owner Not Found"));

        owner.setGymName(updatedOwner.getGymName());
        owner.setOwnerName(updatedOwner.getOwnerName());
        owner.setPhone(updatedOwner.getPhone());
        owner.setProfilePhoto(updatedOwner.getProfilePhoto());



        return gymOwnerRepository.save(owner);


    }
}