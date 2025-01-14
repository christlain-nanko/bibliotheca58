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
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service class for managing books in the Bibliotheca58 application.
 *
 * This class handles business logic for operations such as creating,
 * updating, retrieving, and deleting books.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    /**
     * Retrieves a book by their ID.
     *
     * @param id the ID of the book to retrieve
     * @return the details of the book as a {@link BookResponseDto}
     * @throws NoSuchElementException if no book is found with the given ID
     */
    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book  with ID:" + id + "not found"));

        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                book.getPrice(),
                book.getAuthor().getName(),
                book.getStatus()
        );
    }

    /**
     * Retrieves a book by their title.
     *
     * @param title the title of the book to retrieve
     * @return the details of the book as a {@link BookResponseDto}
     * @throws NoSuchElementException if no book is found with the given title
     */
    public BookResponseDto getBookByTitle(String title){
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException("Book  with ID: " + title + "is not available our library"));

        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                book.getPrice(),
                book.getAuthor().getName(),
                book.getStatus()
        );
    }

    /**
     * Retrieves a list books by their genre.
     *
     * @param genre the genre of the books to retrieve
     * @return a list of {@link BookResponseDto} containing details of all books
     */
    public List<BookResponseDto> getBooksByGenre (String genre) {
        return bookRepository.findByGenre(genre).stream()
                .map(book -> new BookResponseDto(
                        book.getId(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getPrice(),
                        book.getAuthor().getName(),
                        book.getStatus())
                ).toList();
    }

    /**
     * Retrieves a list of books with a price greater than the specified value.
     *
     * @param price the minimum price to filter books
     * @return a list of {@link BookResponseDto} containing books priced greater than the specified value
     */
    public List<BookResponseDto> getBooksByPriceGreaterThan(Double price) {
        return bookRepository.findByPriceGreaterThan(price).stream()
                .map(book  -> new BookResponseDto(
                        book.getId(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getPrice(),
                        book.getAuthor().getName(),
                        book.getStatus())
                ).toList();
    }

    /**
     * Retrieves a list of all books in the library.
     *
     *@return a list of {@link BookResponseDto} containing details of all books
     */
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookResponseDto(
                        book.getId(),
                        book.getTitle(),
                        book.getGenre(),
                        book.getPrice(),
                        book.getAuthor().getName(),
                        book.getStatus())
                ).toList();
    }

    /**
     * Creates a new book in the library by their ID.
     *
     * @param bookRequestDto the details of the book to be created
     * @return a {@link BookResponseDto} containing the created book's details
     * @throws NoSuchElementException if an author with the given ID not found
     * @throws IllegalArgumentException if a book with the same  title and author already exists .
     */
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {

        Optional<Book> existingBook = bookRepository.findByTitleAndAuthorId(
                bookRequestDto.title(),
                bookRequestDto.authorId()
        );

        Author author = authorRepository.findById(bookRequestDto.authorId())
                .orElseThrow(() -> new RuntimeException("Author with ID " + bookRequestDto.authorId() + " not found"));

        if (existingBook.isPresent()) {
            throw new IllegalArgumentException("A book with the title: " + bookRequestDto.title()+ " from  author: " + author.getName() + " already exists.");
        }

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
                newBook.getAuthor().getName(),
                newBook.getStatus()
        );
    }

    /**
     * Updates the details of an existing book.
     *
     * @param id the unique ID of the book to update
     * @param bookRequestDto the updated details of the book
     * @return a {@link BookRequestDto} containing the updated member's details
     * @throws NoSuchElementException if no book or author with the given IDs are found
     */
    public BookResponseDto updateBook( Long id, BookRequestDto bookRequestDto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with ID:" + id + "not found"));

        Author author = authorRepository.findById(bookRequestDto.authorId())
                .orElseThrow(() -> new NoSuchElementException("Author with ID " + bookRequestDto.authorId() + " not found"));

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
                updatedBook.getAuthor().getName(),
                updatedBook.getStatus()
        );
    }

    /**
     * Deletes a book from the library by their ID.
     *
     * @param id the unique ID of the book to delete
     * @throws NoSuchElementException if no book with the given ID is found
     */
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book with ID " + id + " not found"));
        bookRepository.delete(book);
    }
}
