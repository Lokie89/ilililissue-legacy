package com.ilililissue.www.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleIssueRepository extends JpaRepository<SimpleIssue, Long> {
}
