package com.example.ilililissue.domain.comment;

import com.example.ilililissue.domain.issue.Issue;
import lombok.Builder;

@Builder
public class IssueComment implements Comment {
    private Issue issue;
    private String comment;

    @Override
    public int createComment() {
        return 1;
    }
}
