package com.agents58.bibliotheca58.dto;

public record MemberResponseDto(
        Long id,
        String username,
        String email,
        String address,
        String phoneNumber
) {}
