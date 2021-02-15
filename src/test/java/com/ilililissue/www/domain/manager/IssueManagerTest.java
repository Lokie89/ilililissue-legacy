package com.ilililissue.www.domain.manager;

import com.ilililissue.www.domain.issue.SimpleIssue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("관리자 객체 테스트")
public class IssueManagerTest {

    @DisplayName("관리자 이슈 생성")
    @Test
    void createIssueTest() {
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        SimpleIssue socialIssue = SimpleIssue.builder().creator(manager).title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images(new String[]{"image", "image2"}).description("내용").build();
        assertEquals("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어", socialIssue.getTitle());
    }
}
