package com.agents58.bibliotheca58.dto;

import java.time.LocalDate;
import java.util.List;

public record LoanResponseDto(
        Long id,
        LocalDate dateOfLoan,
        LocalDate dateOfReturn,
        String memberUsername,
        List<String> bookTitles
) {}