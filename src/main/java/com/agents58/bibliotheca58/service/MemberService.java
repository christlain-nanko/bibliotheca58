package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.model.Member;
import com.agents58.bibliotheca58.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member with ID:" + id + "not found"));
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Member with Username:" + username + "not found"));
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member with email:" + email + "not found"));
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Member memberDetails) {
        Member member = getMemberById(memberDetails.getId());
        member.setUsername(memberDetails.getUsername());
        member.setEmail(memberDetails.getEmail());
        member.setAddress(memberDetails.getAddress());
        member.setPhoneNumber(memberDetails.getPhoneNumber());
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
