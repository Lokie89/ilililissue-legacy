package com.ilililissue.www.service.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("좋아요 서비스 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties")
public class CommentLikeServiceTest {
    @Autowired
    CommentLikeService commentLikeService;

    @Autowired
    IssueMemberService issueMemberService;

    @Autowired
    IssueManagerService issueManagerService;

    @Autowired
    SimpleIssueService simpleIssueService;

    @Autowired
    IssueCommentService issueCommentService;

    @BeforeEach
    void setUp() {
        IssueMember issueMember = new IssueMember();
        issueMemberService.create(issueMember);
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        issueManagerService.create(manager);

        SimpleIssue socialIssue = SimpleIssue.builder().creator(manager).title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images(new String[]{"image", "image2"}).description("내용").build();
        simpleIssueService.create(socialIssue);
        IssueComment issueComment = IssueComment.builder().author(issueMember).issue(socialIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment);
    }

    @DisplayName("좋아요 생성")
    @Test
    void saveLikeTest() {
        IssueMember issueMember = new IssueMember();
        issueMemberService.create(issueMember);

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);

        CommentLike commentLike = CommentLike.builder().comment(savedIssueComment).member(issueMember).build();

        commentLikeService.createOrCancel(commentLike);
        assertEquals(issueMember, commentLike.getMember());
    }

    @DisplayName("좋아요 취소")
    @Test
    void cancelLikeTest() {
        IssueMember issueMember = new IssueMember();
        issueMemberService.create(issueMember);

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);

        CommentLike commentLike = CommentLike.builder().comment(savedIssueComment).member(issueMember).build();

        commentLikeService.createOrCancel(commentLike);
        CommentLike cancelCommentLike = commentLikeService.createOrCancel(commentLike);
        assertEquals('n', cancelCommentLike.getStatus());
        assertEquals(1, commentLikeService.getAll().size());
    }

    @DisplayName("좋아요 여러개")
    @Test
    void saveLikesTest() {
        IssueMember issueMember1 = IssueMember.builder().name("좋아요1").build();
        IssueMember issueMember2 = IssueMember.builder().name("좋아요2").build();
        IssueMember issueMember3 = IssueMember.builder().name("좋아요3").build();
        issueMemberService.create(issueMember1);
        issueMemberService.create(issueMember2);
        issueMemberService.create(issueMember3);

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);

        CommentLike commentLike1 = CommentLike.builder().comment(savedIssueComment).member(issueMember1).build();
        CommentLike commentLike2 = CommentLike.builder().comment(savedIssueComment).member(issueMember2).build();
        CommentLike commentLike3 = CommentLike.builder().comment(savedIssueComment).member(issueMember3).build();

        commentLikeService.createOrCancel(commentLike1);
        commentLikeService.createOrCancel(commentLike2);
        commentLikeService.createOrCancel(commentLike3);

        assertEquals(3, commentLikeService.getAll().size());
        assertEquals(3, commentLikeService.getCommentLikeListByIssueComment(savedIssueComment).size());
    }
}
