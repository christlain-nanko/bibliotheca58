package com.agents58.bibliotheca58.repository;

import com.agents58.bibliotheca58.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);     // Find member by username
    Optional<Member> findByEmail(String email);     // Find member by email

}
