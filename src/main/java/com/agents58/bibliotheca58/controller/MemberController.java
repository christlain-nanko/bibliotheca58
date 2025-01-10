package com.agents58.bibliotheca58.controller;


import com.agents58.bibliotheca58.model.Member;
import com.agents58.bibliotheca58.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @GetMapping("/username/{username}")
    public Member getMemberByUsername(@PathVariable String username) {
        return memberService.getMemberByUsername(username);
    }

    @GetMapping("email/{email}")
    public Member getMemberByEmail(@PathVariable String email) {
        return memberService.getMemberByEmail(email);
    }

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberService.createMember(member);
    }

    @PutMapping
    public Member updateMember(@RequestBody Member memberDetails) {
        return memberService.updateMember(memberDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
