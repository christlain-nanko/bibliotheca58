package com.agents58.bibliotheca58.dto;

import java.time.LocalDate;

public record AuthorRequestDto(
        String name,
        LocalDate dateOfBirth
) {}
