package com.ilililissue.www.domain.comment;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.member.IssueMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueCommentRepository extends JpaRepository<IssueComment, Long> {
    boolean existsByIssueAndMember(DefaultIssue issue, IssueMember member);
}
