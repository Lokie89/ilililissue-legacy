package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueManagerControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @DisplayName("매니저 생성")
    @Test
    @Order(1)
    void createManagerTest() {
        String url = "/api/v1/manager";
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        HttpEntity<IssueManager> entity = new HttpEntity<>(issueManager);
        ResponseEntity<IssueManager> response = restTemplate.postForEntity(url, entity, IssueManager.class);
        assertEquals(201, response.getStatusCodeValue());
    }

    @DisplayName("매니저 가져오기")
    @Test
    @Order(2)
    void getManagerTest() {
        String url = "/api/v1/manager/1";
        ResponseEntity<IssueManager> response = restTemplate.getForEntity(url, IssueManager.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getId());
    }

}
