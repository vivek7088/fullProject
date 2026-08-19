package com.vivek.gympulse.service;

import com.vivek.gympulse.entity.Member;
import com.vivek.gympulse.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DueGenerationService {

    @Autowired
    private MemberRepository memberRepository;

    public void generateDuePayments() {

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {

            if (member.getNextDueDate() != null
                    && !member.getNextDueDate().isAfter(LocalDate.now())
                    && member.getPendingAmount() == 0) {

                member.setPendingAmount(member.getFeesAmount());
                member.setPaidAmount(0.0);

                memberRepository.save(member);
            }
        }
    }
}