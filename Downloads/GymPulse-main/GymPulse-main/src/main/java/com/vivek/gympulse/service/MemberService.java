package com.vivek.gympulse.service;

import com.vivek.gympulse.dto.DashboardDTO;
import com.vivek.gympulse.entity.GymOwner;
import com.vivek.gympulse.entity.Member;
import com.vivek.gympulse.repository.GymOwnerRepository;
import com.vivek.gympulse.repository.MemberRepository;
import com.vivek.gympulse.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private GymOwnerRepository gymOwnerRepository;

    // Add Member Owner Wise
    public Member saveMember(Long ownerId, Member member) {

        GymOwner owner = gymOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Gym Owner Not Found"));

        member.setGymOwner(owner);

        return memberRepository.save(member);
    }

    // Get All Members Owner Wise
    public List<Member> getAllMembers(Long ownerId) {

        GymOwner owner = gymOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Gym Owner Not Found"));

        List<Member> members = memberRepository.findByGymOwner(owner);

        for (Member member : members) {

            if (member.getExpiryDate() != null &&
                    member.getExpiryDate().isBefore(LocalDate.now())) {

                member.setStatus("EXPIRED");

            } else {

                member.setStatus("ACTIVE");
            }

            memberRepository.save(member);
        }

        return members;
    }

    public DashboardDTO getDashboardData(Long ownerId) {

        GymOwner owner = gymOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Gym Owner Not Found"));

        long totalMembers = memberRepository.countByGymOwner(owner);

        long activeMembers = memberRepository.countByGymOwnerAndStatus(owner, "ACTIVE");

        long expiredMembers = memberRepository.countByGymOwnerAndStatus(owner, "EXPIRED");

        long pendingMembers = memberRepository.countPendingMembersByGymOwner(owner);

        Double totalIncome = paymentRepository.getTotalIncomeByGymOwner(owner);

        Double totalPendingAmount = memberRepository.getTotalPendingAmountByGymOwner(owner);

        return new DashboardDTO(
                totalMembers,
                activeMembers,
                expiredMembers,
                pendingMembers,
                totalIncome,
                totalPendingAmount
        );
    }

    public List<Member> getPendingMembers() {
        return memberRepository.findPendingMembers();
    }

    public Member renewMember(Long id) {

        Member member = memberRepository.findById(id).orElseThrow();

        Integer months = member.getPlanMonths();

        member.setExpiryDate(
                member.getExpiryDate().plusMonths(months)
        );

        member.setStatus("ACTIVE");

        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member updatedMember) {

        Member member = memberRepository.findById(id).orElseThrow();

        member.setName(updatedMember.getName());
        member.setPhone(updatedMember.getPhone());
        member.setFeesAmount(updatedMember.getFeesAmount());
        member.setJoiningDate(updatedMember.getJoiningDate());
        member.setExpiryDate(updatedMember.getExpiryDate());
        member.setStatus(updatedMember.getStatus());
        member.setPlanType(updatedMember.getPlanType());
        member.setPlanMonths(updatedMember.getPlanMonths());

        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}