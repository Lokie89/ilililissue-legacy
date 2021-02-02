package com.ilililissue.www.web.controller;


import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
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
public class IssueControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    private IssueManager createAndGetManager(ManagerRole role) {
        String url = "/api/v1/manager";
        IssueManager issueManager = new IssueManager(role);
        HttpEntity<IssueManager> entity = new HttpEntity<>(issueManager);
        ResponseEntity<IssueManager> response = restTemplate.postForEntity(url, entity, IssueManager.class);
        return response.getBody();
    }

    @DisplayName("이슈 생성하기")
    @Order(1)
    @Test
    void createIssueTest() {
        IssueManager master = createAndGetManager(ManagerRole.MASTER);
        String url = "/api/v1/issue";
        HttpEntity<DefaultIssueSaveDto> request = new HttpEntity<>(DefaultIssueSaveDto.builder().creator(master).title("이슈 제목").images("이미지1", "이미지2").description("반갑습니다.").build());
        ResponseEntity<DefaultIssue> response = restTemplate.postForEntity(url, request, DefaultIssue.class);
        assertEquals("이슈 제목", Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals(201, response.getStatusCodeValue());
    }

    @DisplayName("생성한 이슈 가져오기")
    @Order(2)
    @Test
    void getIssueTest() {
        String url = "/api/v1/issue/1";
        ResponseEntity<DefaultIssue> response = restTemplate.getForEntity(url, DefaultIssue.class);
        assertEquals(1, Objects.requireNonNull(response.getBody()).getId());
        assertEquals(200, response.getStatusCodeValue());
    }

    @DisplayName("권한 LV1 일때 이슈 생성 실패 401")
    @Order(3)
    @Test
    void cannotCreateIssueTest() {
        IssueManager master = createAndGetManager(ManagerRole.LV1);
        String url = "/api/v1/issue";
        HttpEntity<DefaultIssueSaveDto> request = new HttpEntity<>(DefaultIssueSaveDto.builder().creator(master).title("이슈 제목").images("이미지1", "이미지2").description("반갑습니다.").build());
        ResponseEntity<DefaultIssue> response = restTemplate.postForEntity(url, request, DefaultIssue.class);
        assertEquals(401, response.getStatusCodeValue());
    }

}
