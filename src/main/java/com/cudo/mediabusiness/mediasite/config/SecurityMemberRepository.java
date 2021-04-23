package com.cudo.mediabusiness.mediasite.config;

import com.cudo.mediabusiness.mediasite.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityMemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);
}
