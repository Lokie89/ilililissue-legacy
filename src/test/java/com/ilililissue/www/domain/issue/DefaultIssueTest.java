package com.ilililissue.www.domain.issue;

import org.junit.jupiter.api.DisplayName;

@DisplayName("이슈 객체 테스트")
public class DefaultIssueTest {

    // TODO : 테스트 추가
//    @DisplayName("이슈 레벨 제한 예외")
//    @Test
//    void notAuthorizedManagerDefaultIssueTest() {
//        IssueManager issueManager = new IssueManager(ManagerRole.LV1);
//        assertThrows(NotAuthorizedManagerException.class, () -> DefaultIssue.builder().creator(issueManager).title("33").build());
//    }
//
//    @DisplayName("이슈 레벨 없을 때 예외")
//    @Test
//    void notAuthorizedManagerDefaultIssueTest2() {
//        IssueManager issueManager = new IssueManager();
//        assertThrows(NullPointerException.class, () -> DefaultIssue.builder().creator(issueManager).title("33").build());
//    }


}
