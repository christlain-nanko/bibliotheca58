package com.agents58.bibliotheca58.controller;


import com.agents58.bibliotheca58.dto.MemberRequestDto;
import com.agents58.bibliotheca58.dto.MemberResponseDto;
import com.agents58.bibliotheca58.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

/**
 * Controller for managing members in the Bibliotheca58 application.
 *
 * Provides endpoints for creating, updating, retrieving, and deleting members.
 */
@RestController
@RequestMapping("api/members")
@RequiredArgsConstructor
@Tag(name = "Members", description = "Endpoints for managing members in Bibliotheca58")
public class MemberController {

    private final MemberService memberService;

    /**
     * Retrieves all members in the library.
     *
     * @return a list of {@link MemberResponseDto} containing details of all members
     */
    @GetMapping
    @Operation(summary = "Get All Members", description = "Retrieve all members")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of members retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<MemberResponseDto> getAllMembers() {
        return memberService.getAllMembers();
    }

    /**
     * Retrieves a member by their unique ID.
     *
     * @param id the unique ID of the member
     * @return a {@link MemberResponseDto} containing the member's details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Member by id", description = "Retrieve a member by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public MemberResponseDto getMember(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    /**
     * Retrieves a member by their username.
     *
     * @param username the username of the member
     * @return a {@link MemberResponseDto} containing the member's details
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "Get Member by username", description = "Retrieve a member by username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public MemberResponseDto getMemberByUsername(@PathVariable String username) {
        return memberService.getMemberByUsername(username);
    }

    /**
     * Retrieves a member by their username.
     *
     * @param email the email of the member
     * @return a {@link MemberResponseDto} containing the member's details
     */
    @GetMapping("email/{email}")
    @Operation(summary = "Get Member by email", description = "Retrieve a member by email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member found"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public MemberResponseDto getMemberByEmail(@PathVariable String email) {
        return memberService.getMemberByEmail(email);
    }

    /**
     * Creates a new member.
     *
     * @param memberRequestDto the details of the member to be created
     * @return a {@link MemberResponseDto} containing the details of the created member
     */
    @PostMapping
    @Operation(summary = "Create Member", description = "Create a new member")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Member created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid member data")
    })
    public MemberResponseDto createMember(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.createMember(memberRequestDto);
    }

    /**
     * Updates an existing member.
     *
     * @param id the ID of the member to update
     * @param memberRequestDto the updated member details
     * @return a {@link MemberResponseDto} containing the updated member's details
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update Member", description = "Update the details of an existing member by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public MemberResponseDto updateMember(@PathVariable Long id, @RequestBody MemberRequestDto memberRequestDto) {
        return memberService.updateMember(id, memberRequestDto);
    }

    /**
     * Deletes a member by their ID.
     *
     * @param id the unique ID of the member to delete
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Member", description = "Delete an existing member by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}
