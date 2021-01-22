package com.example.ilililissue.domain.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "application-test.properties")
public class IssueManagerRepositoryTest {
    @Autowired
    IssueManagerRepository repository;

    @Test
    void saveManagerTest() {
        IssueManager issueManager = new IssueManager();
        repository.save(issueManager);
        assertEquals(1, issueManager.getId());
    }
}
