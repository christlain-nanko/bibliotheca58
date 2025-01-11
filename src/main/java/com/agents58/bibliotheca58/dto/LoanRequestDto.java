package com.agents58.bibliotheca58.dto;

import java.time.LocalDate;
import java.util.Set;

public record LoanRequestDto(
        Long memberId,
        Set<Long> bookIds,
        LocalDate date
) {}
