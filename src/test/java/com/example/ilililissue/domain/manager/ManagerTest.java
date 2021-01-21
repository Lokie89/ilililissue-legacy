package com.example.ilililissue.domain.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerTest {

    @Test
    void createIssueTest() {
        Issue socialIssue = DefaultIssue.builder().title().images().description().build();
        Manager manager = new IssueManager();
        int created = manager.create(socialIssue);

        assertEquals(1, created);
    }
}
