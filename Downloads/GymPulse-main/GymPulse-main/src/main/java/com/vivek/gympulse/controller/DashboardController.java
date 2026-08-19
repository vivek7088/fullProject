package com.vivek.gympulse.controller;

import com.vivek.gympulse.dto.DashboardDTO;
import com.vivek.gympulse.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/{ownerId}")
    public DashboardDTO getDashboard(@PathVariable Long ownerId) {
        return memberService.getDashboardData(ownerId);
    }
}