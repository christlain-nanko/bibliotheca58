package com.agents58.bibliotheca58.controller;

import com.agents58.bibliotheca58.dto.BookRequestDto;
import com.agents58.bibliotheca58.dto.BookResponseDto;
import com.agents58.bibliotheca58.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing books in the Bibliotheca58 application.
 *
 * Provides endpoints for creating, updating, retrieving, and deleting books.
 */
@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Endpoints for managing books in Bibliotheca58")
public class BookController {

    private final BookService bookService;

    /**
     * Retrieves all books in the library.
     *
     * @return a list of {@link BookResponseDto} containing details of all books
     */
    @GetMapping
    @Operation(summary = "Get All Books", description = "Retrieve all books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of books retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }


    /**
     * Retrieves a book by its ID.
     *
     * @param id the unique ID of the book
     * @return a {@link BookResponseDto} containing the book's details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Book by id", description = "Retrieve a book by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public BookResponseDto getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    /**
     * Retrieves a list of books by their genre.
     *
     * @param genre the genre to filter books
     * @return a list of {@link BookResponseDto} containing books matching the specified genre
     */
    @GetMapping("/genre/{genre}")
    @Operation(summary = "Get Book by Genre", description = "Retrieve a book by genre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public List<BookResponseDto> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }


    /**
     * Retrieves a book by title .
     *
     * @param title the title of the book
     * @return a {@link BookResponseDto} containing the book's details
     */
    @GetMapping("/title/{title}")
    @Operation(summary = "Get Book by Title", description = "Retrieve a book by title")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public BookResponseDto getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    /**
     * Retrieves a list of books with a price greater than the given value.
     *
     * @param price the minimum price to filter books
     * @return a list of {@link BookResponseDto} containing books priced greater than the given value
     */
    @GetMapping("/price/{price}")
    @Operation(summary = "Get all Books whose price Greater than the given value",
            description = "Retrieve a books whose price Greater than the given value")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of books retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid price value"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<BookResponseDto> getBooksByPriceGreaterThan(@PathVariable Double price) {
        return bookService.getBooksByPriceGreaterThan(price);
    }


    /**
     * Creates a new book.
     *
     * @param bookRequestDto the details of the book to be created
     * @return a {@link BookResponseDto} containing the details of the created book
     */
    @PostMapping
    @Operation(summary = "Create Book", description = "Create a new Book")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid book data")
    })
    public BookResponseDto createBook(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.createBook(bookRequestDto);
    }

    /**
     * Updates an existing book.
     *
     * @param id the ID of the book to update
     * @param bookRequestDto the updated book details
     * @return a {@link BookResponseDto} containing the updated book's details
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update Book",
            description = "Update the details of an existing book by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public BookResponseDto updateBook(@PathVariable Long id, @RequestBody BookRequestDto bookRequestDto) {
        return bookService.updateBook(id, bookRequestDto);
    }

    /**
     * Deletes a book by ID.
     *
     * @param id the ID of the book to delete
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Book", description = "Delete an existing book by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }


}
