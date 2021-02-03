package com.ilililissue.www.web.dto;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueCommentDeleteDto {
    private IssueComment issueComment;
    private IssueMember author;
}
