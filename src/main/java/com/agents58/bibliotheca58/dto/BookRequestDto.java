package com.agents58.bibliotheca58.dto;

public record BookRequestDto(
        String title,
        String genre,
        Double price,
        Long authorId
) {}
