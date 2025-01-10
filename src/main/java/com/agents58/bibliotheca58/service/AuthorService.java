package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.model.Author;
import com.agents58.bibliotheca58.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with ID:" + id + "not found"));
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Author with name :" + name + " not found"));
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author authorDetails) {
        Author author = getAuthorById(authorDetails.getId());
        author.setName(authorDetails.getName());
        author.setDateOfBirth(authorDetails.getDateOfBirth());
        author.setBooks(authorDetails.getBooks());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

}
