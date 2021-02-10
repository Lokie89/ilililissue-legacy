package com.ilililissue.www.domain.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("좋아요 객체 테스트")
public class CommentLikeTest {
    @DisplayName("좋아요 객체 생성")
    @Test
    void createLikeCommentTest() {
        IssueManager manager = new IssueManager(ManagerRole.MASTER);

        IssueMember likeMember = new IssueMember();

        IssueMember issueMember = new IssueMember();
        DefaultIssue socialIssue = DefaultIssue.builder().creator(manager).title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images(new String[]{"image", "image2"}).description("내용").build();
        IssueComment issueComment = IssueComment.builder().author(issueMember).issue(socialIssue).comment("코로나 스탑!!").build();

        CommentLike commentLike = CommentLike.builder().member(likeMember).comment(issueComment).build();

        assertEquals("코로나 스탑!!", commentLike.getComment().getComment());

    }
}
