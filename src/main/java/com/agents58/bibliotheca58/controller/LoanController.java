package com.agents58.bibliotheca58.controller;


import com.agents58.bibliotheca58.dto.LoanRequestDto;
import com.agents58.bibliotheca58.dto.LoanResponseDto;
import com.agents58.bibliotheca58.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public List<LoanResponseDto> getAllLoans () {
        return loanService.getAllLoans();
    }

    @GetMapping("/{loanId}")
    public LoanResponseDto getLoan(@PathVariable Long loanId) {
        return loanService.getLoanById(loanId);
    }

    @GetMapping("/member/{memberId}")
    public List<LoanResponseDto> getLoanByMemberId(@PathVariable Long memberId){
        return loanService.getLoansByMemberId(memberId);
    }

    @PostMapping
    public LoanResponseDto createLoan(@RequestBody LoanRequestDto loanRequestDto) {
        return loanService.createLoan(loanRequestDto);
    }

    @PutMapping("/return/{loanId}")
    public LoanResponseDto returnLoan(@PathVariable Long loanId) {
        return loanService.returnLoan(loanId);
    }
}
