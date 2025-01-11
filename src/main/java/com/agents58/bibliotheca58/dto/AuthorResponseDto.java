package com.agents58.bibliotheca58.dto;

import java.time.LocalDate;

public record AuthorResponseDto(
        Long id,
        String name,
        LocalDate dateOfBirth
) {}
