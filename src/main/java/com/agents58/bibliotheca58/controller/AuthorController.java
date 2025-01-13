package com.agents58.bibliotheca58.controller;

import com.agents58.bibliotheca58.dto.AuthorRequestDto;
import com.agents58.bibliotheca58.dto.AuthorResponseDto;
import com.agents58.bibliotheca58.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing authors in the Bibliotheca58 application.
 *
 * Provides endpoints for creating, updating, retrieving, and deleting authors.
 */
@RestController
@RequestMapping("api/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Endpoints for managing authors in Bibliotheca58")
public class AuthorController {

    private final AuthorService authorService;


    /**
     * Retrieves all authors in the library.
     *
     * @return a list of {@link AuthorResponseDto} containing details of all authors
     */
    @GetMapping
    @Operation(summary = "Get All Authors", description = "Retrieve all authors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of authors retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<AuthorResponseDto> getAllAuthors () {
        return authorService.getAllAuthors();
    }

    /**
     * Retrieves an author by name.
     *
     * @param name the name of the author
     * @return a {@link AuthorResponseDto} containing the author's details
     */
    @GetMapping("name/{name}")
    @Operation(summary = "Get Author by name", description = "Retrieve a author by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public AuthorResponseDto getAuthorByName(@PathVariable String name) {
        return authorService.getAuthorByName(name);
    }

    /**
     * Retrieves an author by their ID.
     *
     * @param id the ID of the author
     * @return a {@link AuthorResponseDto} containing the author's details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Author by id", description = "Retrieve a author by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public AuthorResponseDto getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    /**
     * Creates a new author.
     *
     * @param authorRequestDto the details of the author to be created
     * @return a {@link AuthorResponseDto} containing the details of the created author
     */
    @PostMapping
    @Operation(summary = "Create Author", description = "Create a new author")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid author data")
    })
    public AuthorResponseDto createAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.createAuthor(authorRequestDto);
    }

    /**
     * Updates an existing author.
     *
     * @param id the unique ID of the author to update
     * @param authorRequestDto the updated author details
     * @return a {@link AuthorResponseDto} containing the updated author's details
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update Author", description = "Update the details of an existing author by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public AuthorResponseDto updateAuthor(@PathVariable Long id, @RequestBody AuthorRequestDto authorRequestDto) {
        return authorService.updateAuthor(id, authorRequestDto);
    }

    /**
     * Deletes an author by their ID.
     *
     * @param id the ID of the author to delete
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Author", description = "Delete an existing author by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found")
    })
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
