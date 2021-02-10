package com.ilililissue.www.service.issue;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;

import com.ilililissue.www.exception.issue.NotAuthorizedManagerException;
import com.ilililissue.www.service.manager.IssueManagerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("이슈 서비스 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties")
public class DefaultIssueServiceTest {
    @Autowired
    DefaultIssueService defaultIssueService;

    @Autowired
    IssueManagerService issueManagerService;

    @DisplayName("이슈 생성")
    @Test
    void saveTest() {
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        IssueManager savedIssueManager = issueManagerService.create(issueManager);
        DefaultIssue defaultIssue = DefaultIssue.builder().creator(savedIssueManager).title("여긴 제목").images(null).description("설명").build();
        defaultIssueService.create(defaultIssue);
        assertEquals("여긴 제목", defaultIssueService.getAll().get(0).getTitle());
    }

    @DisplayName("이슈 레벨 제한 예외")
    @Test
    void notAuthorizedManagerDefaultIssueTest() {
        IssueManager issueManager = new IssueManager(ManagerRole.LV1);
        IssueManager savedIssueManager = issueManagerService.create(issueManager);
        DefaultIssue defaultIssue = DefaultIssue.builder().creator(savedIssueManager).title("여긴 제목").images(null).description("설명").build();
        assertThrows(NotAuthorizedManagerException.class, () -> defaultIssueService.create(defaultIssue));
    }

    @DisplayName("이슈 레벨 없을 때 예외")
    @Test
    void notAuthorizedManagerDefaultIssueTest2() {
        IssueManager issueManager = new IssueManager();
        IssueManager savedIssueManager = issueManagerService.create(issueManager);
        DefaultIssue defaultIssue = DefaultIssue.builder().creator(savedIssueManager).title("여긴 제목").images(null).description("설명").build();
        assertThrows(NullPointerException.class, () -> defaultIssueService.create(defaultIssue));
    }

}
