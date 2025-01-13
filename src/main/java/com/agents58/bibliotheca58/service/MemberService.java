package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.dto.MemberRequestDto;
import com.agents58.bibliotheca58.dto.MemberResponseDto;
import com.agents58.bibliotheca58.model.Member;
import com.agents58.bibliotheca58.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


/**
 * Service class for managing members in the Bibliotheca58 application.
 *
 * This class handles business logic for operations such as creating,
 * updating, retrieving, and deleting members.
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * Retrieves a member by their ID.
     *
     * @param id the ID of the member to retrieve
     * @return the details of the member as a {@link MemberResponseDto}
     * @throws NoSuchElementException if no member is found with the given ID
     */
    public MemberResponseDto getMemberById(Long id) {
        Member member =  memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member with ID:" + id + "not found"));

        return new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getAddress(),
                member.getPhoneNumber()
        );
    }

    /**
     * Retrieves a member by their username.
     *
     * @param username the username of the member to retrieve
     * @return the details of the member as a {@link MemberResponseDto}
     * @throws NoSuchElementException if no member is found with the given username
     */
    public MemberResponseDto getMemberByUsername(String username) {
        Member member =  memberRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Member with Username:" + username + "not found"));

        return new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getAddress(),
                member.getPhoneNumber()
        );
    }

    /**
     * Retrieves a member by their email.
     *
     * @param email the email of the member to retrieve
     * @return the details of the member as a {@link MemberResponseDto}
     * @throws NoSuchElementException if no member is found with the given email
     */
    public MemberResponseDto getMemberByEmail(String email) {
        Member member =  memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Member with email:" + email + "not found"));

        return new MemberResponseDto(
                member.getId(),
                member.getUsername(),
                member.getEmail(),
                member.getAddress(),
                member.getPhoneNumber()
        );
    }

    /**
     * Retrieves a list of all member in the library.
     *
     *@return a list of {@link MemberResponseDto} containing details of all members
     */
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

    /**
     * Creates a new member in the library by their ID.
     *
     * @param memberRequestDto the details of the member to be created
     * @return a {@link MemberResponseDto} containing the created member's details
     */
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

    /**
     * Updates the details of an existing member.
     *
     * @param id the unique ID of the member to update
     * @param memberRequestDto the updated details of the member
     * @return a {@link MemberResponseDto} containing the updated member's details
     * @throws NoSuchElementException if no member with the given ID is found
     */
    public MemberResponseDto updateMember(Long id , MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member with ID " + id + " not found"));

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

    /**
     * Deletes a member from the library by their ID.
     *
     * @param id the unique ID of the member to delete
     * @throws NoSuchElementException if no member with the given ID is found
     */
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member with ID " + id + " not found"));
        memberRepository.delete(member);
    }
}
