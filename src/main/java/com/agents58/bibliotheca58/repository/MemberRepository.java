package com.agents58.bibliotheca58.repository;

import com.agents58.bibliotheca58.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username); //check if member exist with the given username
    boolean existsByEmail(String email); //check if member exist with the given email
    Optional<Member> findByUsername(String username);     // Find member by username
    Optional<Member> findByEmail(String email);     // Find member by email

}
