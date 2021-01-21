package com.example.ilililissue.domain.like;

import com.example.ilililissue.domain.comment.Comment;
import com.example.ilililissue.domain.comment.IssueComment;
import com.example.ilililissue.domain.issue.DefaultIssue;
import com.example.ilililissue.domain.issue.Issue;
import com.example.ilililissue.domain.member.IssueMember;
import com.example.ilililissue.domain.member.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LikeTest {
    @Test
    void createLikeCommentTest() {
        Member likeMember = new IssueMember();

        Member issueMember = new IssueMember();
        Issue socialIssue = DefaultIssue.builder().title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        Comment issueComment = IssueComment.builder().member(issueMember).issue(socialIssue).comment("코로나 스탑!!").build();

        Like commentLike = CommentLike.builder().member(likeMember).comment(issueComment).build();

        int created = commentLike.createLike();
        assertEquals(1, created);

    }
}