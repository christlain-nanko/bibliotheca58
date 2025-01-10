package com.agents58.bibliotheca58.repository;

import com.agents58.bibliotheca58.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name); // Find Author  by name
}
