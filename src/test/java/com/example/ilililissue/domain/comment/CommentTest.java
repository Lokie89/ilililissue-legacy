package com.example.ilililissue.domain.comment;

import com.example.ilililissue.domain.issue.DefaultIssue;
import com.example.ilililissue.domain.issue.Issue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentTest {
    @Test
    void createIssueCommentTest() {
        Issue socialIssue = DefaultIssue.builder().title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        Comment issueComment = IssueComment.builder().issue(socialIssue).comment("코로나 스탑!!").build();
        int createdComment = issueComment.comment(socialIssue);
        assertEquals(1, createdComment);
    }

}
