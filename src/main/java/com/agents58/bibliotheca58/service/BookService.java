package com.agents58.bibliotheca58.service;


import com.agents58.bibliotheca58.dto.BookRequestDto;
import com.agents58.bibliotheca58.dto.BookResponseDto;
import com.agents58.bibliotheca58.model.Author;
import com.agents58.bibliotheca58.model.Book;
import com.agents58.bibliotheca58.repository.AuthorRepository;
import com.agents58.bibliotheca58.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book  with ID:" + id + "not found"));

        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                book.getPrice(),
                book.getAuthor().getName()
        );
    }

    public BookResponseDto getBookByTitle(String title){
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book  with ID: " + title + "is not available our library"));

        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                book.getPrice(),
                book.getAuthor().getName()
        );
    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookResponseDto(
                        book.getId(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getPrice(),
                        book.getAuthor().getName())
                ).toList();
    }

    public List<BookResponseDto> getBooksByGenre (String genre) {
        return bookRepository.findByGenre(genre).stream()
                .map(book -> new BookResponseDto(
                        book.getId(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getPrice(),
                        book.getAuthor().getName())
                ).toList();
    }

    public BookResponseDto createBook(BookRequestDto bookRequestDto) {

        Author author = authorRepository.findById(bookRequestDto.authorId())
                .orElseThrow(() -> new RuntimeException("Author with ID " + bookRequestDto.authorId() + " not found"));

        Book book = new Book();
        book.setTitle(bookRequestDto.title());
        book.setGenre(bookRequestDto.genre());
        book.setPrice(bookRequestDto.price());
        book.setAuthor(author);
        Book newBook = bookRepository.save(book);

        return new BookResponseDto(
                newBook.getId(),
                newBook.getTitle(),
                newBook.getGenre(),
                newBook.getPrice(),
                newBook.getAuthor().getName()
        );
    }

    public List<BookResponseDto> getBooksByPriceGreaterThan(Double price) {
        return bookRepository.findByPriceGreaterThan(price).stream()
                .map(book  -> new BookResponseDto(
                        book.getId(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getPrice(),
                        book.getAuthor().getName())
                ).toList();
    }

    public BookResponseDto updateBook( Long id, BookRequestDto bookRequestDto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with ID:" + id + "not found"));

        Author author = authorRepository.findById(bookRequestDto.authorId())
                .orElseThrow(() -> new RuntimeException("Author with ID " + bookRequestDto.authorId() + " not found"));

        book.setTitle(bookRequestDto.title());
        book.setGenre(bookRequestDto.genre());
        book.setPrice(bookRequestDto.price());
        book.setAuthor(author);
        Book updatedBook = bookRepository.save(book);

        return new BookResponseDto(
                updatedBook.getId(),
                updatedBook.getTitle(),
                updatedBook.getGenre(),
                updatedBook.getPrice(),
                updatedBook.getAuthor().getName()
        );
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
