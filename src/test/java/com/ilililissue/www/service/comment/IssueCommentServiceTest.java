package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import com.ilililissue.www.exception.comment.CanNotUpdateCommentException;
import com.ilililissue.www.service.issue.IssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "application-test.properties")
public class IssueCommentServiceTest {

    @Autowired
    IssueCommentService issueCommentService;

    @Autowired
    IssueManagerService issueManagerService;

    @Autowired
    IssueService issueService;

    @Autowired
    IssueMemberService issueMemberService;

    @BeforeEach
    void setUp() {
        IssueMember issueMember = new IssueMember();
        issueMemberService.create(issueMember);
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        issueManagerService.create(manager);
        DefaultIssue socialIssue = DefaultIssue.builder(manager, "신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        issueService.create(socialIssue);
    }

    private void createIssueComment() {
        IssueMember issueMember = issueMemberService.getAll().get(0);
        DefaultIssue getIssue = issueService.getAll().get(0);
        IssueComment issueComment = IssueComment.builder().author(issueMember).issue(getIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment);
    }

    @Test
    void saveIssueComment() {
        createIssueComment();
        assertEquals("코로나 스탑!!", issueCommentService.getAll().get(0).getComment());
    }

    @Test
    void cannotRegisterComment() {
        IssueMember issueMember = issueMemberService.getAll().get(0);
        DefaultIssue getIssue = issueService.getAll().get(0);
        IssueComment issueComment1 = IssueComment.builder().author(issueMember).issue(getIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment1);
        IssueComment issueComment2 = IssueComment.builder().author(issueMember).issue(getIssue).comment("코로나 스탑!! 두번째!!!").build();
        assertThrows(CanNotRegisterCommentException.class, () -> issueCommentService.create(issueComment2));
    }

    @Test
    void updateComment() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        savedIssueComment.setComment("코로나 백신 언제나오냐!!");
        issueCommentService.update(savedIssueComment);
        IssueComment updatedIssueComment = issueCommentService.getAll().get(0);
        assertEquals("코로나 백신 언제나오냐!!", updatedIssueComment.getComment());
    }

    @Test
    void cannotUpdateComment() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        savedIssueComment.setComment("코로나 백신 언제나오냐!!");
        issueCommentService.update(savedIssueComment);
        IssueComment updatedIssueComment = issueCommentService.getAll().get(0);
        updatedIssueComment.setComment("코로나 백신 안나올거같아 진짜");
        assertThrows(CanNotUpdateCommentException.class, () -> issueCommentService.update(updatedIssueComment));
    }

    @Test
    void removeCommentTest() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        IssueMember issueMember = issueMemberService.getAll().get(0);
        issueCommentService.remove(savedIssueComment, issueMember);

        IssueComment deletedIssueComment = issueCommentService.getAll().get(0);
        assertEquals('n', deletedIssueComment.getStatus());
    }

    @Test
    void cannotRemoveCommentTest() {
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        IssueMember issueMember = new IssueMember();
        assertThrows(CanNotRemoveCommentException.class, () -> issueCommentService.remove(savedIssueComment, issueMember));
    }

    @Test
    void removeCommentByManagerTest(){
        createIssueComment();

        IssueComment savedIssueComment = issueCommentService.getAll().get(0);
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        issueCommentService.remove(savedIssueComment, issueManager);

        IssueComment deletedIssueComment = issueCommentService.getAll().get(0);
        assertEquals('n', deletedIssueComment.getStatus());
    }
}
