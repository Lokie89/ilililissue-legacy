package com.ilililissue.www.domain.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentAndMember(IssueComment comment, IssueMember member);
    CommentLike findByCommentAndMember(IssueComment comment, IssueMember member);
}
