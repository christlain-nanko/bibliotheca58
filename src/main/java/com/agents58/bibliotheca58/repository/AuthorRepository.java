package com.agents58.bibliotheca58.repository;

import com.agents58.bibliotheca58.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name); // Find Author  by name
    boolean existsByNameAndDateOfBirth(String name, LocalDate dateOfBirth); // check if an author exist by name and date of birth

}
