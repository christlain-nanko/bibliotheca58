package com.agents58.bibliotheca58.repository;

import com.agents58.bibliotheca58.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);     // Find book by title
    List<Book> findByGenre(String genre);        // Find books by genre
    List<Book> findByPriceGreaterThan(Double price); // Find books with price greater than the  given value
}
