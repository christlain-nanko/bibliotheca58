package com.agents58.bibliotheca58.controller;


import com.agents58.bibliotheca58.model.Loan;
import com.agents58.bibliotheca58.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public List<Loan> getAllLoans () {
        return loanService.getAllLoans();
    }

    @GetMapping("/{loanId}")
    public Loan getLoan(@PathVariable Long loanId) {
        return loanService.getLoanById(loanId);
    }

    @GetMapping("/member/{memberId}")
    public List<Loan> getLoanByMemberId(@PathVariable Long memberId){
        return loanService.getLoansByMemberId(memberId);
    }

    @PostMapping
    public Loan createLoan(@RequestParam Long memberId, @RequestParam Set<Long> bookIds) {
        return loanService.createLoan(memberId, bookIds);
    }

    @PutMapping("/return/{loanId}")
    public Loan returnLoan(@PathVariable Long loanId) {
        return loanService.returnLoan(loanId);
    }


}
