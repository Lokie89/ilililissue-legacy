package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.service.issue.IssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    void setUp() {
        IssueMember issueMember = new IssueMember();
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        issueManagerService.create(manager);
        DefaultIssue socialIssue = DefaultIssue.builder(manager, "신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        issueService.create(socialIssue);
    }

    @Test
    void saveIssueComment() {

        IssueMember issueMember = new IssueMember();
        DefaultIssue getIssue = issueService.getAll().get(0);
        IssueComment issueComment = IssueComment.builder().member(issueMember).issue(getIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment);
        assertEquals("코로나 스탑!!", issueCommentService.getAll().get(0).getComment());
    }

    @Test
    void cannotRegisterComment() {
        IssueMember issueMember = new IssueMember();
        DefaultIssue getIssue = issueService.getAll().get(0);
        IssueComment issueComment1 = IssueComment.builder().member(issueMember).issue(getIssue).comment("코로나 스탑!!").build();
        issueCommentService.create(issueComment1);
        IssueComment issueComment2 = IssueComment.builder().member(issueMember).issue(getIssue).comment("코로나 스탑!! 두번째!!!").build();
        assertThrows(CanNotRegisterCommentException.class, () -> issueCommentService.create(issueComment2));
    }
}
