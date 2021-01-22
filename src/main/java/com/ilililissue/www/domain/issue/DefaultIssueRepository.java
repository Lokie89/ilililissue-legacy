package com.ilililissue.www.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultIssueRepository extends JpaRepository<DefaultIssue, Long> {
}
