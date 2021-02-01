package com.ilililissue.www.domain.issue;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.exception.issue.NotAuthorizedManagerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultIssueTest {

    @DisplayName("이슈 레벨 제한 예외")
    @Test
    void notAuthorizedManagerDefaultIssueTest() {
        IssueManager issueManager = new IssueManager(ManagerRole.LV1);
        assertThrows(NotAuthorizedManagerException.class, () -> DefaultIssue.builder(issueManager, "33").build());
    }

    @DisplayName("이슈 레벨 없을 때 예외")
    @Test
    void notAuthorizedManagerDefaultIssueTest2() {
        IssueManager issueManager = new IssueManager();
        assertThrows(NullPointerException.class, () -> DefaultIssue.builder(issueManager, "33").build());
    }


}
