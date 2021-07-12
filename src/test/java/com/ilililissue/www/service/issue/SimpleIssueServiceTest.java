package com.ilililissue.www.service.issue;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.member.IssueMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("이슈 서비스 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties")
public class SimpleIssueServiceTest {
    @Autowired
    SimpleIssueService simpleIssueService;

    @Autowired
    IssueMemberService issueMemberService;

    @DisplayName("이슈 생성")
    @Test
    void saveTest() {
        IssueMember issueManager = new IssueMember();
        IssueMember savedIssueManager = issueMemberService.create(issueManager);
        SimpleIssue simpleIssue = SimpleIssue.builder().creator(savedIssueManager).title("여긴 제목").images(null).description("설명").build();
        simpleIssueService.create(simpleIssue);
        assertEquals("여긴 제목", simpleIssueService.getAll().get(0).getTitle());
    }

//    @DisplayName("이슈 레벨 제한 예외")
//    @Test
//    void notAuthorizedManagerDefaultIssueTest() {
//        IssueManager issueManager = new IssueManager(ManagerRole.LV1);
//        IssueManager savedIssueManager = issueManagerService.create(issueManager);
//        SimpleIssue simpleIssue = SimpleIssue.builder().creator(savedIssueManager).title("여긴 제목").images(null).description("설명").build();
//        assertThrows(NotAuthorizedManagerException.class, () -> simpleIssueService.create(simpleIssue));
//    }
//
//    @DisplayName("이슈 레벨 없을 때 예외")
//    @Test
//    void notAuthorizedManagerDefaultIssueTest2() {
//        IssueManager issueManager = new IssueManager();
//        IssueManager savedIssueManager = issueManagerService.create(issueManager);
//        SimpleIssue simpleIssue = SimpleIssue.builder().creator(savedIssueManager).title("여긴 제목").images(null).description("설명").build();
//        assertThrows(NullPointerException.class, () -> simpleIssueService.create(simpleIssue));
//    }

}
