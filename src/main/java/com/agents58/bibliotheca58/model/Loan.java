package com.agents58.bibliotheca58.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateOfLoan;
    private LocalDate dateOfReturn;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "loan_books",
            joinColumns = @JoinColumn(name = "loan_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();


    public Loan (LocalDate dateOfLoan, Member member) {
        this.dateOfLoan = dateOfLoan;
        this.member = member;
    }

}
