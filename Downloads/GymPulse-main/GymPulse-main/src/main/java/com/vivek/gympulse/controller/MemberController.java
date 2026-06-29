package com.vivek.gympulse.controller;

import com.vivek.gympulse.entity.Member;
import com.vivek.gympulse.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // Add Member
    @PostMapping("/add/{ownerId}")
    public Member addMember(@PathVariable Long ownerId,
                            @RequestBody Member member) {
        return memberService.saveMember(ownerId, member);
    }

    // Get All Members Owner Wise
    @GetMapping("/all/{ownerId}")
    public List<Member> getAllMembers(@PathVariable Long ownerId) {
        return memberService.getAllMembers(ownerId);
    }

    // Pending Members
    @GetMapping("/pending")
    public List<Member> getPendingMembers() {
        return memberService.getPendingMembers();
    }

    // Renew Member
    @PostMapping("/renew/{id}")
    public Member renewMember(@PathVariable Long id) {
        return memberService.renewMember(id);
    }

    // Update Member
    @PutMapping("/update/{id}")
    public Member updateMember(@PathVariable Long id,
                               @RequestBody Member member) {
        return memberService.updateMember(id, member);
    }

    // Delete Member
    @DeleteMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return "Member Deleted Successfully";
    }
}