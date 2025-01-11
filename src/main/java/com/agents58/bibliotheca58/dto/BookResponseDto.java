package com.agents58.bibliotheca58.dto;

public record BookResponseDto(
        Long id,
        String title,
        String genre,
        Double price,
        String authorName
) {}
