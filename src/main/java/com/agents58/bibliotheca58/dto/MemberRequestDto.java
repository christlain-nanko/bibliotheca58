package com.agents58.bibliotheca58.dto;

public record MemberRequestDto(
        String username,
        String email,
        String address,
        String phoneNumber
) {}
