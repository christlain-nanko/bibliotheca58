package com.agents58.bibliotheca58.service;

import com.agents58.bibliotheca58.dto.LoanRequestDto;
import com.agents58.bibliotheca58.dto.LoanResponseDto;
import com.agents58.bibliotheca58.model.Book;
import com.agents58.bibliotheca58.model.BookStatus;
import com.agents58.bibliotheca58.model.Loan;
import com.agents58.bibliotheca58.model.Member;
import com.agents58.bibliotheca58.repository.BookRepository;
import com.agents58.bibliotheca58.repository.LoanRepository;
import com.agents58.bibliotheca58.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Service class for managing loans in the Bibliotheca58 application.
 *
 * This class handles business logic for operations such as creating,
 * returning, retrieving, and deleting loan.
 */
@Service
@RequiredArgsConstructor
public class LoanService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;


    /**
     * Retrieves a loan by their ID.
     *
     * @param id the ID of the loan to retrieve
     * @return the details of the loan as a {@link LoanResponseDto }
     * @throws NoSuchElementException if no loan is found with the given ID
     */
    public LoanResponseDto getLoanById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));

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

    /**
     * Retrieves a list of all loans in the library.
     *
     *@return a list of {@link LoanResponseDto} containing details of all Loans
     */
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

    /**
     * Retrieves a list of loans associated with a specific member by their ID.
     *
     * @param id the unique ID of the member whose loans are to be retrieved
     * @return a list of {@link LoanResponseDto} containing the details of the member's loans
     * @throws NoSuchElementException if no member with the given ID is found
     */
    public List<LoanResponseDto> getLoansByMemberId(Long id) {

       memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member with ID " + id + " not found"));

        return loanRepository.findByMemberId(id).stream()
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

    /**
     * Creates a new loan for a member with the specified books.
     *
     * @param loanRequestDto the details of the loan, including the member ID, book IDs, and lend date
     * @return a {@link LoanResponseDto} containing the details of the created loan
     * @throws NoSuchElementException if the member or one or more books are not found
     * @throws IllegalStateException if the member has already loaned the maximum allowed number of books or
     * book in the collection is already lent out
     */
    public LoanResponseDto createLoan(LoanRequestDto loanRequestDto) {
        Member member = memberRepository.findById(loanRequestDto.memberId())
                .orElseThrow(() -> new NoSuchElementException("Member with ID:" + loanRequestDto.memberId() + "not found"));

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

        List<Book> books = bookRepository.findAllById(loanRequestDto.bookIds());

        // check if books exist
        if (books.size() != loanRequestDto.bookIds().size()) {
            throw new NoSuchElementException ("One or more books not found for the given IDs: " + loanRequestDto.bookIds());
        }

        // check if books already lent out
        for (Book book : books) {
            if (book.getStatus() == BookStatus.LENT) {
                throw new IllegalStateException("Book '" + book.getTitle() + "' is already lent out.");
            }
        }

        // mark books as lent
        for (Book book : books) {
            book.setStatus(BookStatus.LENT);
        }
        bookRepository.saveAll(books);

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


    /**
     * Marks a loan as returned by setting the current date.
     *
     * @param id the ID of the loan to be returned
     * @return a {@link LoanResponseDto} containing the updated loan details
     * @throws NoSuchElementException if the loan with the given ID is not found
     * @throws IllegalStateException if the loan has already been returned
     */
    public LoanResponseDto returnLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Loan with ID " + id + " not found"));

        if (loan.getDateOfReturn() != null) {
            throw new IllegalStateException("Loan with ID " + id + " has already been returned on " + loan.getDateOfReturn());
        }

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
