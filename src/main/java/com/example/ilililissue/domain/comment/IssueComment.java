package com.example.ilililissue.domain.comment;

import com.example.ilililissue.domain.issue.Issue;
import com.example.ilililissue.domain.member.Member;
import lombok.Builder;

@Builder
public class IssueComment implements Comment {
    private Member member;
    private Issue issue;
    private String comment;

    @Override
    public int createComment() {
        return 1;
    }
}
