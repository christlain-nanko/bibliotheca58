package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.dto.AuthorRequestDto;
import com.agents58.bibliotheca58.dto.AuthorResponseDto;
import com.agents58.bibliotheca58.model.Author;
import com.agents58.bibliotheca58.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing authors in the Bibliotheca58 application.
 *
 * This class handles business logic for operations such as creating,
 * updating, retrieving, and deleting authors.
 */
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Retrieves an author by their ID.
     *
     * @param id the ID of the author to retrieve
     * @return the details of the author as a {@link AuthorResponseDto }
     * @throws NoSuchElementException if no author is found with the given ID
     */
    public AuthorResponseDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author with ID:" + id + "not found"));
        return new AuthorResponseDto(
                author.getId(),
                author.getName(),
                author.getDateOfBirth()
        );
    }

    /**
     * Retrieves an author by their name.
     *
     * @param name the name of the author to retrieve
     * @return the details of the author as a {@link AuthorResponseDto}
     * @throws NoSuchElementException if no author is found with the given name
     */
    public AuthorResponseDto getAuthorByName(String name) {
        Author author = authorRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Author with name :" + name + " not found"));
        return new AuthorResponseDto(
                author.getId(),
                author.getName(),
                author.getDateOfBirth()
        );
    }

    /**
     * Retrieves a list of all authors in the library.
     *
     *@return a list of {@link AuthorResponseDto} containing details of all authors
     */
    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(author -> new AuthorResponseDto(
                        author.getId(),
                        author.getName(),
                        author.getDateOfBirth())
                ).toList();
    }

    /**
     * Creates a new author in the library by their ID.
     *
     * @param authorRequestDto the details of the author to be created
     * @return a {@link AuthorResponseDto} containing the created author's details
     * @throws IllegalArgumentException if author with the same name and date already exists
     */
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {

        if (authorRepository.existsByNameAndDateOfBirth(authorRequestDto.name(), authorRequestDto.dateOfBirth())) {
            throw new IllegalArgumentException("An author with the same name and date of birth already exists.");
        }
        Author author = new Author();
        author.setName(authorRequestDto.name());
        author.setDateOfBirth(authorRequestDto.dateOfBirth());
        Author newAuthor = authorRepository.save(author);
        return new AuthorResponseDto(
                newAuthor.getId(),
                newAuthor.getName(),
                newAuthor.getDateOfBirth()
        );
    }

    /**
     * Updates the details of an existing author.
     *
     * @param id the unique ID of the author to update
     * @param authorRequestDto the updated details of the author
     * @return a {@link AuthorResponseDto} containing the updated author's details
     * @throws NoSuchElementException if no author with the given ID is found
     */
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto authorRequestDto) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author with ID " + id + " not found"));

        author.setName(authorRequestDto.name());
        author.setDateOfBirth(authorRequestDto.dateOfBirth());
        Author updatedAuthor = authorRepository.save(author);

        return new AuthorResponseDto(
                updatedAuthor.getId(),
                updatedAuthor.getName(),
                updatedAuthor.getDateOfBirth()
        );
    }

    /**
     * Deletes an author from the library by their ID.
     *
     * @param id the unique ID of the author to delete
     * @throws NoSuchElementException if no author with the given ID is found
     */
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author with ID " + id + " not found"));
        authorRepository.delete(author);
    }

}
