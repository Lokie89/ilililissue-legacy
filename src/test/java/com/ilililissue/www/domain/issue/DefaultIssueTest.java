package com.ilililissue.www.domain.issue;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultIssueTest {

    @Test
    void notAuthorizedManagerDefaultIssueTest() {
        IssueManager issueManager = new IssueManager(ManagerRole.LV1);
        assertThrows(NotAuthorizedManagerException.class, () -> DefaultIssue.builder(issueManager, "33").build());
    }

    @Test
    void notAuthorizedManagerDefaultIssueTest2() {
        IssueManager issueManager = new IssueManager();
        assertThrows(NotAuthorizedManagerException.class, () -> DefaultIssue.builder(issueManager, "33").build());
    }


}
