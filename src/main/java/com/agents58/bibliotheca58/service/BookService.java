package com.agents58.bibliotheca58.service;


import com.agents58.bibliotheca58.model.Book;
import com.agents58.bibliotheca58.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book  with ID:" + id + "not found"));
    }

    public Book getBookByTitle(String title){
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book  with ID: " + title + "is not available our library"));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByGenre (String genre) {
        return bookRepository.findByGenre(genre);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooksByPriceGreaterThan(Double price) {
        return bookRepository.findByPriceGreaterThan(price);
    }

    public Book updateBook( Book bookDetails) {
        Book book = getBookById(bookDetails.getId());
        book.setTitle(bookDetails.getTitle());
        book.setGenre(bookDetails.getGenre());
        book.setPrice(bookDetails.getPrice());
        book.setAuthor(bookDetails.getAuthor());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
