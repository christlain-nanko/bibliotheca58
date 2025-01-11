package com.agents58.bibliotheca58.controller;

import com.agents58.bibliotheca58.dto.AuthorRequestDto;
import com.agents58.bibliotheca58.dto.AuthorResponseDto;
import com.agents58.bibliotheca58.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorResponseDto> getAllAuthors () {
        return authorService.getAllAuthors();
    }

    @GetMapping("name/{name}")
    public AuthorResponseDto getAuthorByName(@PathVariable String name) {
        return authorService.getAuthorByName(name);
    }

    @GetMapping("/{id}")
    public AuthorResponseDto getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public AuthorResponseDto createAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.createAuthor(authorRequestDto);
    }

    @PutMapping("/{id}")
    public AuthorResponseDto updateAuthor(@PathVariable Long id, @RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.updateAuthor(id, authorRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
