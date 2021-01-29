package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueManagerControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void createManagerTest() {
        String url = "/api/v1/issue/manager";
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        HttpEntity<IssueManager> entity = new HttpEntity<>(issueManager);
        ResponseEntity<IssueManager> response = restTemplate.postForEntity(url, entity, IssueManager.class);
        assertEquals(201, response.getStatusCodeValue());
    }

}
