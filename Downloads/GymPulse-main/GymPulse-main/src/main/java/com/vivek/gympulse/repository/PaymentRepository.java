package com.vivek.gympulse.repository;

import com.vivek.gympulse.entity.GymOwner;
import com.vivek.gympulse.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.status='PAID'")
    Double getTotalIncome();

    List<Payment> findByMemberId(Long memberId);

    @Query("""
            SELECT COALESCE(SUM(p.amount),0)
            FROM Payment p
            WHERE p.member.gymOwner = :gymOwner
            AND p.status='PAID'
            """)
    Double getTotalIncomeByGymOwner(GymOwner gymOwner);
}