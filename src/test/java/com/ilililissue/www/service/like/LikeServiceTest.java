package com.ilililissue.www.service.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.issue.IssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "application-test.properties")
public class LikeServiceTest {
    @Autowired
    LikeService likeService;

    @Autowired
    IssueMemberService issueMemberService;

    @Autowired
    IssueManagerService issueManagerService;

    @Autowired
    IssueService issueService;

    @Autowired
    IssueCommentService issueCommentService;

    @BeforeEach
    void setUp() {
        IssueMember issueMember = new IssueMember();
        issueMemberService.create(issueMember);
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        issueManagerService.create(manager);
        DefaultIssue socialIssue = DefaultIssue.builder(manager, "신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        issueService.create(socialIssue);
        IssueComment issueComment = IssueComment.builder().author(issueMember).issue(socialIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment);
    }

    @Test
    void saveLikeTest() {
        IssueMember issueMember = new IssueMember();
        issueMemberService.create(issueMember);

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);

        CommentLike commentLike = CommentLike.builder().comment(savedIssueComment).member(issueMember).build();

        likeService.create(commentLike);
    }
}
