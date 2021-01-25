package com.ilililissue.www.domain.manager;

import com.ilililissue.www.domain.issue.DefaultIssue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerTest {

    @Test
    void createIssueTest() {
        IssueManager manager = new IssueManager(ManagerRole.MASTER);
        DefaultIssue socialIssue = DefaultIssue.builder(manager, "신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        assertEquals("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어", socialIssue.getTitle());
    }
}
