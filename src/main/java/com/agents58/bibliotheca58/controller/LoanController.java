package com.agents58.bibliotheca58.controller;


import com.agents58.bibliotheca58.dto.LoanRequestDto;
import com.agents58.bibliotheca58.dto.LoanResponseDto;
import com.agents58.bibliotheca58.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing loans in the Bibliotheca58 application.
 *
 * Provides endpoints for creating, retrieving, and returning loans.
 */
@RestController
@RequestMapping("api/loans")
@RequiredArgsConstructor
@Tag(name = "Loans", description = "Endpoints for managing Loans in Bibliotheca58")
public class LoanController {

    private final LoanService loanService;


    /**
     * Retrieves all loans in the library.
     *
     * @return a list of {@link LoanResponseDto} containing details of all loans
     */
    @GetMapping
    @Operation(summary = "Get All Loans", description = "Retrieve all loans")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of loans retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<LoanResponseDto> getAllLoans () {
        return loanService.getAllLoans();
    }

    /**
     * Retrieves a loan by its ID.
     *
     * @param id th ID of the loan
     * @return a {@link LoanResponseDto} containing the loan details
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Loan by id", description = "Retrieve a loan by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan found"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    public LoanResponseDto getLoan(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    /**
     * Retrieves all loans associated with a specific member by their ID.
     *
     * @param id the ID of the member
     * @return a list of {@link LoanResponseDto} containing the loans for the specified member
     */
    @GetMapping("/member/{id}")
    @Operation(summary = "Get Loans by Member ID",
            description = "Retrieve all loans for a specific member by their ID")
    public List<LoanResponseDto> getLoanByMemberId(@PathVariable Long id){
        return loanService.getLoansByMemberId(id);
    }

    /**
     * Creates a new loan for a member.
     *
     * @param loanRequestDto the details of the loan to be created, including member ID and book IDs
     * @return a {@link LoanResponseDto} containing the details of the created loan
     */
    @PostMapping
    @Operation(summary = "Create Loan", description = "Create a new Loan")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Loan created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid loan data"),
            @ApiResponse(responseCode = "404", description = "Member or book(s) not found")
    })
    public LoanResponseDto createLoan(@RequestBody LoanRequestDto loanRequestDto) {
        return loanService.createLoan(loanRequestDto);
    }

    /**
     * Returns a loan by setting the current date.
     *
     * @param id the ID of the loan to be returned
     * @return a {@link LoanResponseDto} containing the updated loan details
     */
    @PutMapping("/return/{id}")
    @Operation(summary = "Return Loan", description = "Mark a loan as returned by setting the return date")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan returned successfully"),
            @ApiResponse(responseCode = "400", description = "Loan already returned"),
            @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    public LoanResponseDto returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }
}
