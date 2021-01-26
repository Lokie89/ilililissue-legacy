package com.ilililissue.www.service.manager;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "application-test.properties")
public class IssueManagerServiceTest {

    @Autowired
    IssueManagerService issueManagerService;

    @Test
    void saveIssueManager() {
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        issueManagerService.save(issueManager);
        assertEquals(issueManager, issueManagerService.getAll().get(0));
    }

    @Test
    void getAllIssueManager() {
        IssueManager issueManager1 = new IssueManager(ManagerRole.MASTER);
        issueManagerService.save(issueManager1);
        IssueManager issueManager2 = new IssueManager(ManagerRole.MASTER);
        issueManagerService.save(issueManager2);
        List<IssueManager> issueManagers = issueManagerService.getAll();
        assertEquals(2, issueManagers.size());
    }

}
