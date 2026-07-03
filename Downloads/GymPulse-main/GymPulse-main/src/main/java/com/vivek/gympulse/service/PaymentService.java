package com.vivek.gympulse.service;

import com.vivek.gympulse.entity.Member;
import com.vivek.gympulse.entity.Payment;
import com.vivek.gympulse.repository.MemberRepository;
import com.vivek.gympulse.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vivek.gympulse.entity.GymOwner;
import com.vivek.gympulse.repository.GymOwnerRepository;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private GymOwnerRepository gymOwnerRepository;

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
        if (payment.getAmount() > member.getPendingAmount()) {
            throw new RuntimeException("Payment exceeds pending amount");
        }
        if (payment.getAmount().equals(member.getPendingAmount())) {
            payment.setPaymentType("FULL");
        } else {
            payment.setPaymentType("PARTIAL");
        }
        // Payment save
        Payment savedPayment = paymentRepository.save(payment);
        // Overpayment Validation

        // Paid amount update
        double paid = member.getPaidAmount() + payment.getAmount();
        member.setPaidAmount(paid);

        // Pending amount update
        double pending = member.getFeesAmount() - paid;
        member.setPendingAmount(pending);
        // Payment complete ho gayi to next due date fix cycle me badhao
        if (member.getPendingAmount() == 0) {

            member.setNextDueDate(
                    member.getNextDueDate().plusMonths(member.getPlanMonths())
            );

            member.setExpiryDate(member.getNextDueDate());

            member.setPaidAmount(0.0);

//member.setPendingAmount(member.getFeesAmount());
        }

        // Membership status sirf expiry ke hisab se hoga
        if (member.getExpiryDate() != null &&
                member.getExpiryDate().isBefore(java.time.LocalDate.now())) {

            member.setStatus("EXPIRED");

        } else {

            member.setStatus("ACTIVE");
        }

        // Member save
        memberRepository.save(member);

        return savedPayment;
    }

    public List<Payment> getPaymentsByMember(Long memberId) {
        return paymentRepository.findByMemberId(memberId);
    }

    public List<Payment> getPaymentsByGymOwner(Long ownerId) {

        GymOwner owner = gymOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Gym Owner Not Found"));

        return paymentRepository.findAllByGymOwnerOrderByPaymentDateDesc(owner);
    }
    public List<Payment> getRecentPayments(Long ownerId) {

        GymOwner owner = gymOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Gym Owner Not Found"));

        return paymentRepository.findTop5RecentPayments(owner);
    }

}

