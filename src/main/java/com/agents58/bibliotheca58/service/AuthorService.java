package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.dto.AuthorRequestDto;
import com.agents58.bibliotheca58.dto.AuthorResponseDto;
import com.agents58.bibliotheca58.model.Author;
import com.agents58.bibliotheca58.model.Book;
import com.agents58.bibliotheca58.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponseDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with ID:" + id + "not found"));
        return new AuthorResponseDto(
                author.getId(),
                author.getName(),
                author.getDateOfBirth()
        );
    }

    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(author -> new AuthorResponseDto(
                        author.getId(),
                        author.getName(),
                        author.getDateOfBirth())
                ).toList();
    }

    public AuthorResponseDto getAuthorByName(String name) {
        Author author = authorRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Author with name :" + name + " not found"));
        return new AuthorResponseDto(
                author.getId(),
                author.getName(),
                author.getDateOfBirth()
        );
    }

    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
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

    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto authorRequestDto) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));

        author.setName(authorRequestDto.name());
        author.setDateOfBirth(authorRequestDto.dateOfBirth());
        Author updatedAuthor = authorRepository.save(author);

        return new AuthorResponseDto(
                updatedAuthor.getId(),
                updatedAuthor.getName(),
                updatedAuthor.getDateOfBirth()
        );
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found"));
        authorRepository.delete(author);
    }

}
