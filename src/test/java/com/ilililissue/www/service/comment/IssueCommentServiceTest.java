package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import com.ilililissue.www.exception.comment.CanNotUpdateCommentException;
import com.ilililissue.www.service.issue.DefaultIssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("댓글 서비스 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties")
public class IssueCommentServiceTest {

    @Autowired
    IssueCommentService issueCommentService;

    @Autowired
    IssueManagerService issueManagerService;

    @Autowired
    DefaultIssueService defaultIssueService;

    @Autowired
    IssueMemberService issueMemberService;

    @BeforeEach
    void setUp() {
        IssueMember issueMember = new IssueMember();
        issueMemberService.create(issueMember);
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        issueManagerService.create(manager);
        DefaultIssue socialIssue = DefaultIssue.builder(manager, "신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        defaultIssueService.create(socialIssue);
    }

    private void createIssueComment() {
        IssueMember issueMember = issueMemberService.getAll().get(0);
        DefaultIssue getIssue = defaultIssueService.getAll().get(0);
        IssueComment issueComment = IssueComment.builder().author(issueMember).issue(getIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment);
    }

    @DisplayName("댓글 생성")
    @Test
    void saveIssueComment() {
        createIssueComment();
        assertEquals("코로나 스탑!!", issueCommentService.getAll().get(0).getComment());
    }

    @DisplayName("댓글 2개 이상 예외")
    @Test
    void cannotRegisterComment() {
        IssueMember issueMember = issueMemberService.getAll().get(0);
        DefaultIssue getIssue = defaultIssueService.getAll().get(0);
        IssueComment issueComment1 = IssueComment.builder().author(issueMember).issue(getIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment1);
        IssueComment issueComment2 = IssueComment.builder().author(issueMember).issue(getIssue).comment("코로나 스탑!! 두번째!!!").build();
        assertThrows(CanNotRegisterCommentException.class, () -> issueCommentService.create(issueComment2));
    }

    @DisplayName("댓글 업데이트")
    @Test
    void updateComment() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        issueCommentService.updateComment(savedIssueComment, "코로나 백신 언제나오냐!!");
        IssueComment updatedIssueComment = issueCommentService.getAll().get(0);
        assertEquals("코로나 백신 언제나오냐!!", updatedIssueComment.getComment());
    }

    @DisplayName("댓글 2회 이상 업데이트 예외")
    @Test
    void cannotUpdateComment() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        issueCommentService.updateComment(savedIssueComment, "코로나 백신 언제나오냐!!312");
        IssueComment updatedIssueComment = issueCommentService.getAll().get(0);
        assertThrows(CanNotUpdateCommentException.class, () -> issueCommentService.updateComment(updatedIssueComment, "코로나 백신 안나올거같아 진짜"));
    }

    @DisplayName("댓글 삭제")
    @Test
    void removeCommentTest() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        IssueMember issueMember = issueMemberService.getAll().get(0);
        issueCommentService.remove(savedIssueComment, issueMember);

        IssueComment deletedIssueComment = issueCommentService.getAll().get(0);
        assertEquals('n', deletedIssueComment.getStatus());
    }

    @DisplayName("댓글 다른 사람 삭제 예외")
    @Test
    void cannotRemoveCommentTest() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        IssueMember issueMember = new IssueMember();
        assertThrows(CanNotRemoveCommentException.class, () -> issueCommentService.remove(savedIssueComment, issueMember));
    }

    @DisplayName("댓글 매니저 삭제")
    @Test
    void removeCommentByManagerTest() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        issueCommentService.remove(savedIssueComment, issueManager);

        IssueComment deletedIssueComment = issueCommentService.getAll().get(0);
        assertEquals('d', deletedIssueComment.getStatus());
    }

    @DisplayName("댓글 매니저 레벨 제한 삭제 예외")
    @Test
    void cannotRemoveCommentByManagerTest() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        IssueManager issueManager = new IssueManager(ManagerRole.LV1);

        assertThrows(CanNotRemoveCommentException.class, () -> issueCommentService.remove(savedIssueComment, issueManager));
    }
}
