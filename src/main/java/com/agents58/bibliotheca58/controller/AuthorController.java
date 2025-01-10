package com.agents58.bibliotheca58.controller;

import com.agents58.bibliotheca58.model.Author;
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
    public List<Author> getAllAuthors () {
        return authorService.getAllAuthors();
    }

    @GetMapping("name/{name}")
    public Author getAuthorByName(@PathVariable String name) {
        return authorService.getAuthorByName(name);
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @PutMapping
    public Author updateAuthor(@RequestBody Author authorDetails) {
        return authorService.updateAuthor(authorDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
