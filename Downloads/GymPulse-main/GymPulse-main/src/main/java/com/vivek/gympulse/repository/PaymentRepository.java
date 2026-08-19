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
    @Query("""
SELECT COALESCE(SUM(p.amount),0)
FROM Payment p
WHERE p.member.gymOwner = :gymOwner
AND p.paymentDate = CURRENT_DATE
AND p.status = 'PAID'
""")
    Double getTodayCollection(GymOwner gymOwner);
    @Query("""
SELECT p
FROM Payment p
WHERE p.member.gymOwner = :gymOwner
ORDER BY p.paymentDate DESC
""")
    List<Payment> findRecentPayments(GymOwner gymOwner);
    @Query("""
SELECT COALESCE(SUM(p.amount),0)
FROM Payment p
WHERE p.member.gymOwner = :gymOwner
AND MONTH(p.paymentDate) = MONTH(CURRENT_DATE)
AND YEAR(p.paymentDate) = YEAR(CURRENT_DATE)
AND p.status = 'PAID'
""")
    Double getCurrentMonthCollection(GymOwner gymOwner);

    @Query("""
SELECT COUNT(p)
FROM Payment p
WHERE p.member.gymOwner = :gymOwner
AND MONTH(p.paymentDate) = MONTH(CURRENT_DATE)
AND YEAR(p.paymentDate) = YEAR(CURRENT_DATE)
AND p.status = 'PAID'
""")
    long getCurrentMonthTransactions(GymOwner gymOwner);

    @Query("""
SELECT p
FROM Payment p
WHERE p.member.gymOwner = :gymOwner
ORDER BY p.paymentDate DESC
""")
    List<Payment> findAllByGymOwnerOrderByPaymentDateDesc(GymOwner gymOwner);

    @Query("""
SELECT p
FROM Payment p
WHERE p.member.gymOwner = :gymOwner
ORDER BY p.paymentDate DESC, p.id DESC
LIMIT 5
""")
    List<Payment> findTop5RecentPayments(GymOwner gymOwner);

}