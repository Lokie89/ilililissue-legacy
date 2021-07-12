package com.ilililissue.www.web.restcontroller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.member.IssueMemberService;
import com.ilililissue.www.web.dto.request.issue.SimpleIssueSaveRequest;
import com.ilililissue.www.web.dto.response.SimpleIssueResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
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
public class SimpleIssueControllerTest {

    @Autowired
    IssueMemberService issueMemberService;

    @Autowired
    WebApplicationContext applicationContext;

    MockMvc mockMvc;

    private final String urlPrefix = "/api/v1/issues";

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
        IssueMember master = issueMemberService.create(new IssueMember());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sign", master);
        SimpleIssueSaveRequest issue = SimpleIssueSaveRequest.builder().title("이슈 제목").build();
        MvcResult response = mockMvc
                .perform(post(urlPrefix)
                        .session(session)
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
        MvcResult response = mockMvc
                .perform(get(urlPrefix + "/1"))
                .andReturn();
        System.out.println(response.getResponse().getContentAsString());
        SimpleIssueResponse savedIssue = new ObjectMapper().readValue(response.getResponse().getContentAsString(), SimpleIssueResponse.class);
        assertEquals(200, response.getResponse().getStatus());
        assertEquals(1L, savedIssue.getId());
    }

    @DisplayName("권한 LV1 일때 이슈 생성 실패 401")
    @Order(3)
    @Test
    void cannotCreateIssueTest() throws Exception {
        IssueMember master = issueMemberService.create(new IssueMember());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sign", master);
        SimpleIssueSaveRequest issue = SimpleIssueSaveRequest.builder().title("이슈 제목").build();
        MvcResult response = mockMvc
                .perform(post(urlPrefix)
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(issue))
                )
                .andReturn();
        assertEquals(401, response.getResponse().getStatus());
    }

}
