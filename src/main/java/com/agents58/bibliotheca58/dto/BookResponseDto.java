package com.agents58.bibliotheca58.dto;

import com.agents58.bibliotheca58.model.BookStatus;

public record BookResponseDto(
        Long id,
        String title,
        String genre,
        Double price,
        String authorName,
        BookStatus status
) {}
