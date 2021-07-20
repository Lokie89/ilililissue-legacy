package com.ilililissue.www.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssueMemberRepository extends JpaRepository<IssueMember, Long> {
    Optional<IssueMember> findByName(String name);
}
