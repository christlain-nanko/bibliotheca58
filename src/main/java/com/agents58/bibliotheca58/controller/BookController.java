package com.agents58.bibliotheca58.controller;

import com.agents58.bibliotheca58.dto.BookRequestDto;
import com.agents58.bibliotheca58.dto.BookResponseDto;
import com.agents58.bibliotheca58.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponseDto getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
    @GetMapping("/genre/{genre}")
    public List<BookResponseDto> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }
    @GetMapping("/title/{title}")
    public BookResponseDto getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/price/{price}")
    public List<BookResponseDto> getBooksByPriceGreaterThan(@PathVariable Double price) {
        return bookService.getBooksByPriceGreaterThan(price);
    }

    @PostMapping
    public BookResponseDto createBook(@RequestBody BookRequestDto bookRequestDto) {
        return bookService.createBook(bookRequestDto);
    }

    @PutMapping("/{id}")
    public BookResponseDto updateBook(@PathVariable Long id, @RequestBody BookRequestDto bookRequestDto) {
        return bookService.updateBook(id, bookRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }


}
