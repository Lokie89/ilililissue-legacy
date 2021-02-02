package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.member.IssueMember;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("멤버 컨트롤러 테스트")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueMemberControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @DisplayName("멤버 생성")
    @Order(1)
    @Test
    void createMemberTest() {
        String url = "/api/v1/member";
        IssueMember member = new IssueMember("길동이");
        HttpEntity<IssueMember> request = new HttpEntity<>(member);
        ResponseEntity<IssueMember> response = restTemplate.postForEntity(url, request, IssueMember.class);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("길동이", Objects.requireNonNull(response.getBody()).getName());
    }

}
