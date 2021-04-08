package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import com.ilililissue.www.exception.comment.CanNotUpdateCommentException;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import com.ilililissue.www.web.dto.request.comment.IssueCommentPatchRequest;
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
    SimpleIssueService simpleIssueService;

    @Autowired
    IssueMemberService issueMemberService;

    @BeforeEach
    void setUp() {
        IssueMember issueMember = new IssueMember();
        issueMemberService.create(issueMember);
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        IssueManager master = issueManagerService.create(manager);
        SimpleIssue socialIssue = SimpleIssue.builder().creator(master).title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images(new String[]{"image", "image2"}).description("내용").build();
        simpleIssueService.create(socialIssue);
    }

    private void createIssueComment() {
        IssueMember issueMember = issueMemberService.getAll().get(0);
        SimpleIssue getIssue = simpleIssueService.getAll().get(0);
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
        SimpleIssue getIssue = simpleIssueService.getAll().get(0);
        IssueComment issueComment1 = IssueComment.builder().author(issueMember).issue(getIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment1);
        IssueComment issueComment2 = IssueComment.builder().author(issueMember).issue(getIssue).comment("코로나 스탑!! 두번째!!!").build();
        assertThrows(CanNotRegisterCommentException.class, () -> issueCommentService.create(issueComment2));
    }

    @DisplayName("댓글 업데이트")
    @Test
    void patchComment() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        String patchComment = "(수정) 수정된 내용입니다.";
        IssueComment patchRequest = IssueComment.builder().id(savedIssueComment.getId()).comment(patchComment).build();
        issueCommentService.patch(patchRequest);

        IssueComment patchedIssueComment = issueCommentService.getAll().get(0);
        assertEquals(patchComment, patchedIssueComment.getComment());
    }

    @DisplayName("댓글 2회 이상 업데이트 예외")
    @Test
    void cannotPatchComment() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        String patchComment = "(수정) 수정된 내용입니다.";
        IssueComment patchRequest = IssueComment.builder().id(savedIssueComment.getId()).comment(patchComment).build();
        issueCommentService.patch(patchRequest);

        String patchComment2 = "(수정) 수정된 내용입니다.2222";
        IssueComment patchRequest2 = IssueComment.builder().id(savedIssueComment.getId()).comment(patchComment2).build();
        assertThrows(CanNotUpdateCommentException.class, () -> issueCommentService.patch(patchRequest2));
    }

    // TODO : Fix me with Auth
//    @DisplayName("댓글 삭제")
//    @Test
//    void removeCommentTest() {
//        createIssueComment();
//
//        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
//        issueCommentService.remove(savedIssueComment.getId());
//
//        IssueComment deletedIssueComment = issueCommentService.getAll().get(0);
//        assertEquals('n', deletedIssueComment.getStatus());
//    }
//
//    @DisplayName("댓글 다른 사람 삭제 예외")
//    @Test
//    void cannotRemoveCommentTest() {
//        createIssueComment();
//
//        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
//        assertThrows(CanNotRemoveCommentException.class, () -> issueCommentService.remove(savedIssueComment.getId()));
//    }
//
//    @DisplayName("댓글 매니저 삭제")
//    @Test
//    void removeCommentByManagerTest() {
//        createIssueComment();
//
//        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
//        issueCommentService.remove(savedIssueComment.getId());
//
//        IssueComment deletedIssueComment = issueCommentService.getAll().get(0);
//        assertEquals('d', deletedIssueComment.getStatus());
//    }
//
//    @DisplayName("댓글 매니저 레벨 제한 삭제 예외")
//    @Test
//    void cannotRemoveCommentByManagerTest() {
//        createIssueComment();
//
//        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
//        assertThrows(CanNotRemoveCommentException.class, () -> issueCommentService.remove(savedIssueComment.getId()));
//    }
}
