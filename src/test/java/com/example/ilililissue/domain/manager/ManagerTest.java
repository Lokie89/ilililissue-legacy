package com.example.ilililissue.domain.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerTest {

    @Test
    void createIssueTest() {
        Issue socialIssue = DefaultIssue.builder().title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image","image2").description("내용").build();
        Manager manager = new IssueManager();
        int created = manager.createIssue(socialIssue);

        assertEquals(1, created);
    }
}
