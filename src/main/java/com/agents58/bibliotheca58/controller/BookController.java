package com.agents58.bibliotheca58.controller;

import com.agents58.bibliotheca58.model.Book;
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
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }
    @GetMapping("/title/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/price/{price}")
    public List<Book> getBooksByPriceGreaterThan(@PathVariable Double price) {
        return bookService.getBooksByPriceGreaterThan(price);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping
    public Book updateBook( @RequestBody Book bookDetails) {
        return bookService.updateBook(bookDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }


}
