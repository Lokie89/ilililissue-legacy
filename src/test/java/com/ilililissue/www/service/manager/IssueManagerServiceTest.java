package com.ilililissue.www.service.manager;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "application-test.properties")
public class IssueManagerServiceTest {

    @Autowired
    IssueManagerService issueManagerService;

    @DisplayName("관리자 생성")
    @Test
    void saveIssueManager() {
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        issueManagerService.create(issueManager);
        assertEquals(issueManager, issueManagerService.getAll().get(0));
    }

    @DisplayName("관리자 조회")
    @Test
    void getAllIssueManager() {
        IssueManager issueManager1 = new IssueManager(ManagerRole.MASTER);
        issueManagerService.create(issueManager1);
        IssueManager issueManager2 = new IssueManager(ManagerRole.MASTER);
        issueManagerService.create(issueManager2);
        List<IssueManager> issueManagers = issueManagerService.getAll();
        assertEquals(2, issueManagers.size());
    }

}
