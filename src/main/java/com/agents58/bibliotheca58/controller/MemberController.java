package com.agents58.bibliotheca58.controller;


import com.agents58.bibliotheca58.dto.MemberRequestDto;
import com.agents58.bibliotheca58.dto.MemberResponseDto;
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
    public List<MemberResponseDto> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public MemberResponseDto getMember(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @GetMapping("/username/{username}")
    public MemberResponseDto getMemberByUsername(@PathVariable String username) {
        return memberService.getMemberByUsername(username);
    }

    @GetMapping("email/{email}")
    public MemberResponseDto getMemberByEmail(@PathVariable String email) {
        return memberService.getMemberByEmail(email);
    }

    @PostMapping
    public MemberResponseDto createMember(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.createMember(memberRequestDto);
    }

    @PutMapping("/{id}")
    public MemberResponseDto updateMember(@PathVariable Long id, @RequestBody MemberRequestDto memberRequestDto) {
        return memberService.updateMember(id, memberRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
