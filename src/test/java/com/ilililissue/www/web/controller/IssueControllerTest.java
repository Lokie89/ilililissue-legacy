package com.ilililissue.www.web.controller;


import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    private IssueManager createAndGetManager() {
        String url = "/api/v1/issue/manager";
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        HttpEntity<IssueManager> entity = new HttpEntity<>(issueManager);
        ResponseEntity<IssueManager> response = restTemplate.postForEntity(url, entity, IssueManager.class);
        return response.getBody();
    }

    @Order(1)
    @Test
    void createIssueTest() {
        IssueManager master = createAndGetManager();
        String url = "/api/v1/issue";
        HttpEntity<DefaultIssueSaveDto> request = new HttpEntity<>(DefaultIssueSaveDto.builder().creator(master).title("이슈 제목").images("이미지1", "이미지2").description("반갑습니다.").build());
        ResponseEntity<DefaultIssue> response = restTemplate.postForEntity(url, request, DefaultIssue.class);
        assertEquals("이슈 제목", Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals(201, response.getStatusCodeValue());
    }

}
