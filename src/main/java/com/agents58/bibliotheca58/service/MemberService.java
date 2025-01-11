package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.dto.MemberRequestDto;
import com.agents58.bibliotheca58.dto.MemberResponseDto;
import com.agents58.bibliotheca58.model.Member;
import com.agents58.bibliotheca58.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto getMemberById(Long id) {
        Member member =  memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member with ID:" + id + "not found"));

        return new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getAddress(),
                member.getPhoneNumber()
        );
    }

    public MemberResponseDto getMemberByUsername(String username) {
        Member member =  memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Member with Username:" + username + "not found"));

        return new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getAddress(),
                member.getPhoneNumber()
        );
    }

    public MemberResponseDto getMemberByEmail(String email) {
        Member member =  memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member with email:" + email + "not found"));

        return new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getAddress(),
                member.getPhoneNumber()
        );
    }

    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map( member -> new MemberResponseDto(
                        member.getId(),
                        member.getUsername(),
                        member.getEmail(),
                        member.getAddress(),
                        member.getPhoneNumber())
                ).toList();
    }

    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        Member member = new Member();
        member.setUsername(memberRequestDto.username());
        member.setEmail(memberRequestDto.email());
        member.setAddress(memberRequestDto.address());
        member.setPhoneNumber(memberRequestDto.phoneNumber());
        Member newMember = memberRepository.save(member);

        return new MemberResponseDto(
                newMember.getId(),
                newMember.getUsername(),
                newMember.getEmail(),
                newMember.getAddress(),
                newMember.getPhoneNumber()
        );
    }

    public MemberResponseDto updateMember(Long id , MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member with ID " + id + " not found"));

        member.setUsername(memberRequestDto.username());
        member.setEmail(memberRequestDto.email());
        member.setAddress(memberRequestDto.address());
        member.setPhoneNumber(memberRequestDto.phoneNumber());
        Member updatedMember = memberRepository.save(member);

        return new MemberResponseDto(
                updatedMember.getId(),
                updatedMember.getUsername(),
                updatedMember.getEmail(),
                updatedMember.getAddress(),
                updatedMember.getPhoneNumber()
        );
    }

    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member with ID " + id + " not found"));
        memberRepository.delete(member);
    }
}
