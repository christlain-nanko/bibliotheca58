package com.agents58.bibliotheca58;

import com.agents58.bibliotheca58.model.*;
import com.agents58.bibliotheca58.repository.AuthorRepository;
import com.agents58.bibliotheca58.repository.BookRepository;
import com.agents58.bibliotheca58.repository.LoanRepository;
import com.agents58.bibliotheca58.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void createLoan_shouldPersistLoanAndReturnOkStatus() throws Exception {

        Member member = memberRepository
                .save(new Member(
                        null,
                        "john_doe",
                        "john@example.com",
                        "123 Test Str",
                        "123-456")
                );

        Author author = authorRepository
                .save(new Author(
                        "Chris Nanko",
                         LocalDate.of(1995,06,17)
                ));

        Book book1 = bookRepository
                .save(new Book(
                        null,
                        "Docker leicht gemacht",
                        "system Engineering",
                        15.00,
                        author,
                        BookStatus.AVAILABLE
                        )
                );

        Book book2 = bookRepository
                .save(new Book(
                        null,
                        "Kubernetes leicht gemacht",
                        "system Engineering",
                        13.00,
                        author,
                        BookStatus.AVAILABLE)
                );

        String date = "2025-01-13";
        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "memberId": %d,
                            "bookIds": [%d, %d],
                            "date": "%s"
                        }
                        """.formatted(member.getId(), book1.getId(), book2.getId(), date)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.memberUsername").value("john_doe"));


        assertEquals(1, loanRepository.count());
        Loan savedLoan = loanRepository.findAll().get(0);
        assertEquals(2, savedLoan.getBooks().size());
        assertEquals(BookStatus.LENT, bookRepository.findById(book1.getId()).get().getStatus());
    }

    @Test
    void returnLoan_shouldUpdateBooksAndReturnOK() throws Exception {
        Member member = memberRepository
                .save(new Member(
                        null,
                        "john_doe",
                        "john@example.com",
                        "123 Test Str",
                        "123-456")
                );

        Author author = authorRepository
                .save(new Author(
                        "Chris Nanko",
                        LocalDate.of(1995,06,17)
                ));

        Book book1 = bookRepository
                .save(new Book(
                                null,
                                "Docker leicht gemacht",
                                "system Engineering",
                                15.00,
                                author,
                                BookStatus.LENT
                        )
                );

        Book book2 = bookRepository
                .save(new Book(
                        null,
                        "Kubernetes leicht gemacht",
                        "system Engineering",
                        13.00,
                        author,
                        BookStatus.LENT)
                );

        Loan loan = new Loan();
        loan.setMember(member);
        loan.setBooks(new HashSet<>(Set.of(book1, book2)));
        loan.setDateOfLoan(LocalDate.now());
        loanRepository.save(loan);

        mockMvc.perform(put("/api/loans/return/{id}",loan.getId()))
                .andExpect(status().isOk());

        Loan updatedLoan = loanRepository.findById(loan.getId()).orElseThrow();
        assertNotNull(updatedLoan.getDateOfReturn());

        assertEquals(BookStatus.AVAILABLE, bookRepository.findById(book1.getId()).get().getStatus());
        assertEquals(BookStatus.AVAILABLE, bookRepository.findById(book2.getId()).get().getStatus());
    }

}
