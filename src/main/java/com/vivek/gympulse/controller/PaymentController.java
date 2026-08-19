package com.vivek.gympulse.controller;

import com.vivek.gympulse.entity.Payment;
import com.vivek.gympulse.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    public Payment addPayment(@RequestBody Payment payment) {
        return paymentService.savePayment(payment);
    }
    @GetMapping("/member/{memberId}")
    public java.util.List<Payment> getPaymentsByMember(
            @PathVariable Long memberId) {

        return paymentService.getPaymentsByMember(memberId);
    }
    @GetMapping("/owner/{ownerId}")
    public java.util.List<Payment> getPaymentsByGymOwner(
            @PathVariable Long ownerId) {

        return paymentService.getPaymentsByGymOwner(ownerId);
    }
    @GetMapping("/recent/{ownerId}")
    public java.util.List<Payment> getRecentPayments(
            @PathVariable Long ownerId) {

        return paymentService.getRecentPayments(ownerId);
    }
}



