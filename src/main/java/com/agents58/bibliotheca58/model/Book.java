package com.agents58.bibliotheca58.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private Double price;

    @ManyToOne
    @JoinColumn(name =  "author_id")
    private Author author;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;
}
