package com.agents58.bibliotheca58.repository;

import com.agents58.bibliotheca58.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByMemberId(Long memberId);    // Find all loans for a member
}
