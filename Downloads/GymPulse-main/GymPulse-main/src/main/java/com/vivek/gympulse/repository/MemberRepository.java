package com.vivek.gympulse.repository;

import com.vivek.gympulse.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.vivek.gympulse.entity.GymOwner;
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT COUNT(m) FROM Member m WHERE m.status = 'ACTIVE'")
    long countActiveMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE m.status = 'EXPIRED'")
    long countExpiredMembers();
    List<Member> findByExpiryDateBefore(java.time.LocalDate date);

    @Query("SELECT m FROM Member m WHERE m.expiryDate < CURRENT_DATE")
    List<Member> findPendingMembers();
    @Query("SELECT COUNT(m) FROM Member m WHERE m.expiryDate < CURRENT_DATE")
    long countPendingMembers();
    @Query("SELECT COALESCE(SUM(m.feesAmount),0) FROM Member m WHERE m.expiryDate < CURRENT_DATE")
    Double getTotalPendingAmount();
    // Owner wise Members
    List<Member> findByGymOwner(GymOwner gymOwner);

    // Dashboard Owner Wise
    long countByGymOwner(GymOwner gymOwner);

    long countByGymOwnerAndStatus(GymOwner gymOwner, String status);

    @Query("""
SELECT COUNT(m)
FROM Member m
WHERE m.gymOwner = :gymOwner
AND m.expiryDate < CURRENT_DATE
""")
    long countPendingMembersByGymOwner(GymOwner gymOwner);

    @Query("""
SELECT COALESCE(SUM(m.feesAmount),0)
FROM Member m
WHERE m.gymOwner = :gymOwner
AND m.expiryDate < CURRENT_DATE
""")
    Double getTotalPendingAmountByGymOwner(GymOwner gymOwner);
    @Query("""
SELECT m
FROM Member m
WHERE m.gymOwner = :gymOwner
AND (
LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR m.phone LIKE CONCAT('%', :keyword, '%')
)
""")
    List<Member> searchMember(GymOwner gymOwner, String keyword);
}