package com.ilililissue.www.service.issue;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;

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
public class IssueServiceTest {
    @Autowired
    IssueService issueService;

    @Test
    void saveTest() {
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        DefaultIssue defaultIssue = DefaultIssue.builder(issueManager, "여긴 제목").images().description("설명").build();
        issueService.save(defaultIssue);
        assertEquals("여긴 제목", issueService.getAll().get(0).getTitle());
    }

}
