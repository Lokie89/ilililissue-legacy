package com.ilililissue.www.web.dto.request;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueCommentDeleteRequest {
    private IssueComment issueComment;
    private IssueMember author;
}
