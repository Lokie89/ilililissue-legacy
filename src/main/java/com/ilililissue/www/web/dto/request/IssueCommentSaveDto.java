package com.ilililissue.www.web.dto.request;

import com.ilililissue.www.domain.comment.CommentPosition;
import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.CanNotBecomeEntityException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class IssueCommentSaveDto {
    private Long issueId;
    private String comment;
    private String position;

    public IssueComment toEntity(Long authorId) {
        validateId(this.issueId);
        validateId(authorId);
        return IssueComment.builder().issue(DefaultIssue.builder().id(issueId).build()).author(IssueMember.builder().id(authorId).build()).position(CommentPosition.valueOf(position)).comment(this.comment).build();
    }

    private void validateId(Long id) {
        if (Objects.isNull(id)) {
            throw new CanNotBecomeEntityException();
        }
    }
}
