package com.ilililissue.www.domain.comment;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentTest {

    @DisplayName("댓글 객체 생성")
    @Test
    void createIssueCommentTest() {
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        IssueMember issueMember = new IssueMember();
        DefaultIssue socialIssue = DefaultIssue.builder(manager, "신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        IssueComment issueComment = IssueComment.builder().author(issueMember).issue(socialIssue).comment("코로나 스탑!!").build();
        assertEquals("코로나 스탑!!", issueComment.getComment());
    }

    @DisplayName("댓글 객체 생성 비교")
    @Test
    void equalIssueCommentTest() {
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        IssueMember issueMember = new IssueMember();
        DefaultIssue socialIssue = DefaultIssue.builder(manager, "신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        IssueComment issueComment1 = IssueComment.builder().author(issueMember).issue(socialIssue).comment("코로나 스탑!!").build();
        IssueComment issueComment2 = IssueComment.builder().author(issueMember).issue(socialIssue).comment("코로나 스탑!!").build();
        assertEquals(issueComment1, issueComment2);
    }

}
