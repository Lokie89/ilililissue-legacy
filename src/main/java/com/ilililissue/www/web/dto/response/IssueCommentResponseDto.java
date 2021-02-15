package com.ilililissue.www.web.dto.response;

import com.ilililissue.www.domain.comment.CommentPosition;
import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class IssueCommentResponseDto {
    private Long id;
    private IssueMember author;
    private SimpleIssue issue;
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
