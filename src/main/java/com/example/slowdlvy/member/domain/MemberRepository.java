package com.example.slowdlvy.member.domain;

import com.example.slowdlvy.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
}
