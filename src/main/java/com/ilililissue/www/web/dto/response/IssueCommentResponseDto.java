package com.ilililissue.www.web.dto.response;

import com.ilililissue.www.domain.comment.CommentPosition;
import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class IssueCommentResponseDto {
    private Long id;
    private IssueMember author;
    private DefaultIssue issue;
    private String comment;
    private char status;
    private CommentPosition position;

    @Setter
    private List<CommentLike> commentLikeList;

    public IssueCommentResponseDto(IssueComment issueComment) {
        id = issueComment.getId();
        author = issueComment.getAuthor();
        issue = issueComment.getIssue();
        comment = issueComment.getComment();
        status = issueComment.getStatus();
        position = issueComment.getPosition();
    }
}
