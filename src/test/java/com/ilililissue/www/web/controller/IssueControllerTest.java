package com.ilililissue.www.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("이슈 컨트롤러 테스트")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueControllerTest {

    @Autowired
    IssueManagerService issueManagerService;

    @Autowired
    WebApplicationContext applicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build()
        ;
    }

    @DisplayName("이슈 생성하기")
    @Order(1)
    @Test
    void createIssueTest() throws Exception {
        IssueManager master = issueManagerService.create(new IssueManager(ManagerRole.MASTER));
        String url = "/api/v1/issue";
        DefaultIssue issue = DefaultIssue.builder(master,"이슈 제목").build();
        MvcResult response = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(issue))
                )
                .andReturn();
        assertEquals(201, response.getResponse().getStatus());
    }

    @DisplayName("생성한 이슈 가져오기")
    @Order(2)
    @Test
    void getIssueTest() throws Exception {
        String url = "/api/v1/issue/1";
        MvcResult response = mockMvc
                .perform(get(url))
                .andReturn();
        DefaultIssue savedIssue = new ObjectMapper().readValue(response.getResponse().getContentAsString(), DefaultIssue.class);
        assertEquals(200, response.getResponse().getStatus());
        assertEquals(1L, savedIssue.getId());
    }

    // TODO : Session 객체를 넣을것
    @DisplayName("권한 LV1 일때 이슈 생성 실패 401")
    @Order(3)
    @Test
    void cannotCreateIssueTest() throws Exception {
        IssueManager master = issueManagerService.create(new IssueManager(ManagerRole.LV1));
        String url = "/api/v1/issue";
        DefaultIssue issue = DefaultIssue.builder(master, "이슈 제목").build();
        MvcResult response = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(issue))
                )
                .andReturn();
        assertEquals(401, response.getResponse().getStatus());
    }

}
