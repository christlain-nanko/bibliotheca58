package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.dto.LoanRequestDto;
import com.agents58.bibliotheca58.dto.LoanResponseDto;
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


    public LoanResponseDto getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        return new LoanResponseDto(
                loan.getId(),
                loan.getDateOfLoan(),
                loan.getDateOfReturn(),
                loan.getMember().getUsername(),
                loan.getBooks().stream()
                        .map(Book::getTitle)
                        .toList()
        );
    }

    public List<LoanResponseDto> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(loan -> new LoanResponseDto(
                            loan.getId(),
                            loan.getDateOfLoan(),
                            loan.getDateOfReturn(),
                            loan.getMember().getUsername(),
                            loan.getBooks().stream()
                                    .map(Book::getTitle)
                                    .toList())
                ).toList();
    }

    public List<LoanResponseDto> getLoansByMemberId(Long memberId) {
        return loanRepository.findByMemberId(memberId).stream()
                .map( loan -> new LoanResponseDto(
                        loan.getId(),
                        loan.getDateOfLoan(),
                        loan.getDateOfReturn(),
                        loan.getMember().getUsername(),
                        loan.getBooks().stream()
                                .map(Book::getTitle)
                                .toList())
                ).toList();
    }

    public LoanResponseDto createLoan(LoanRequestDto loanRequestDto) {
        Member member = memberRepository.findById(loanRequestDto.memberId())
                .orElseThrow(() -> new RuntimeException("Member with ID:" + memberId + "not found"));

        List<Loan> memberLoans = loanRepository.findByMemberId(member.getId());
        long numberOfBooksOnLoan = memberLoans.stream()
                .flatMap(loan -> loan.getBooks().stream())
                .distinct()
                .count();

        long requestedBookCount = loanRequestDto.bookIds().size();

        if (numberOfBooksOnLoan + requestedBookCount > 5) {
            throw new IllegalStateException("Member cannot loan more than 5 books at a time. Currently loaned: "
                    + numberOfBooksOnLoan + ", Requested: " + requestedBookCount);
        }

        // check if books exist
        List<Book> books = bookRepository.findAllById(loanRequestDto.bookIds());
        if (books.size() != loanRequestDto.bookIds().size()) {
            throw new RuntimeException ("One or more books not found for the given IDs: " + loanRequestDto.bookIds());
        }

        Loan loan = new Loan();
        loan.setMember(member);
        loan.setBooks(new HashSet<>(books));
        loan.setDateOfLoan(loanRequestDto.date());
        loan.setDateOfReturn(null);
        Loan newLoan = loanRepository.save(loan);

        return new LoanResponseDto(
                newLoan.getId(),
                newLoan.getDateOfLoan(),
                newLoan.getDateOfReturn(),
                newLoan.getMember().getUsername(),
                newLoan.getBooks().stream()
                        .map(Book::getTitle)
                        .toList());
    }


    public LoanResponseDto returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan with ID " + loanId + " not found"));
        loan.setDateOfReturn(LocalDate.now());
        Loan updatedLoan = loanRepository.save(loan);

        return new LoanResponseDto(
                updatedLoan.getId(),
                updatedLoan.getDateOfLoan(),
                updatedLoan.getDateOfReturn(),
                updatedLoan.getMember().getUsername(),
                updatedLoan.getBooks().stream()
                        .map(Book::getTitle)
                        .toList()
        );
    }

}
