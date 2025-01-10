package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.model.Book;
import com.agents58.bibliotheca58.model.Loan;
import com.agents58.bibliotheca58.model.Member;
import com.agents58.bibliotheca58.repository.BookRepository;
import com.agents58.bibliotheca58.repository.LoanRepository;
import com.agents58.bibliotheca58.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;


    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoansByMemberId(Long memberId) {
        return loanRepository.findByMemberId(memberId);
    }

    public Loan createLoan(Long memberId, Set<Long> bookIds) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member with ID:" + memberId + "not found"));

        long numberOfBooksOnLoan = loanRepository.findAll().stream()
                .filter(loan -> loan.getMember().getId().equals(memberId))
                .filter(loan -> loan.getDateOfReturn() == null)
                .mapToLong(loan -> loan.getBooks().size())
                .sum();

        if (numberOfBooksOnLoan + bookIds.size() > 5) {
            throw new RuntimeException("Every member can only loan up to 5 different books at a time");
        }

        Set<Book> books = new HashSet<>(bookRepository.findAllById(bookIds));

        Loan loan = new Loan(LocalDate.now(), member);
        loan.getBooks().addAll(books);
        return loanRepository.save(loan);
    }

    public Loan returnLoan(Long loanId) {
        Loan loan = getLoanById(loanId);
        loan.setDateOfReturn(LocalDate.now());
        return loanRepository.save(loan);
    }

}
