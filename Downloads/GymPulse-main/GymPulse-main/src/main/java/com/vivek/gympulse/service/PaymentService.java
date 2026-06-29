package com.vivek.gympulse.service;

import com.vivek.gympulse.entity.Member;
import com.vivek.gympulse.entity.Payment;
import com.vivek.gympulse.repository.MemberRepository;
import com.vivek.gympulse.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Payment savePayment(Payment payment) {

        // Database se member nikalo
        Member member = memberRepository.findById(payment.getMember().getId())
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        // Payment me original member set karo
        payment.setMember(member);

        // Payment save
        Payment savedPayment = paymentRepository.save(payment);

        // Paid amount update
        double paid = member.getPaidAmount() + payment.getAmount();
        member.setPaidAmount(paid);

        // Pending amount update
        double pending = member.getFeesAmount() - paid;
        member.setPendingAmount(pending);

        // Status update
        if (pending <= 0) {
            member.setStatus("PAID");
        } else {
            member.setStatus("PENDING");
        }

        // Member save
        memberRepository.save(member);

        return savedPayment;
    }

    public List<Payment> getPaymentsByMember(Long memberId) {
        return paymentRepository.findByMemberId(memberId);
    }
}