package com.ilililissue.www.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
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

@DisplayName("멤버 컨트롤러 테스트")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueMemberControllerTest {

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

    @DisplayName("멤버 생성")
    @Order(1)
    @Test
    void createMemberTest() throws Exception {
        String url = "/api/v1/member";
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        MvcResult response = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(issueManager))
                )
                .andReturn();
        assertEquals(201, response.getResponse().getStatus());
    }

    @DisplayName("멤버 가져오기")
    @Order(2)
    @Test
    void getMemberTest() throws Exception {
        String url = "/api/v1/member/1";
        MvcResult response = mockMvc
                .perform(get(url))
                .andReturn();
        IssueMember savedMember = new ObjectMapper().readValue(response.getResponse().getContentAsString(), IssueMember.class);
        assertEquals(200, response.getResponse().getStatus());
        assertEquals(1L, savedMember.getId());
    }

}
