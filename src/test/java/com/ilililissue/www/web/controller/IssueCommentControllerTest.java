package com.ilililissue.www.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.issue.DefaultIssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@DisplayName("댓글 컨트롤러 테스트")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueCommentControllerTest {

    @Autowired
    IssueManagerService issueManagerService;

    @Autowired
    IssueMemberService issueMemberService;

    @Autowired
    DefaultIssueService defaultIssueService;

    @Autowired
    IssueCommentService issueCommentService;

    @Autowired
    WebApplicationContext applicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .apply(springSecurity())
                .build()
        ;
    }

    @DisplayName("세팅")
    @Order(1)
    @Test
    void createIssue() {
        IssueManager master = issueManagerService.create(new IssueManager(ManagerRole.MASTER));
        DefaultIssueSaveDto issueSaveDto = DefaultIssueSaveDto.builder().creator(master).title("타이를").build();
        defaultIssueService.create(issueSaveDto);
        IssueMember member = new IssueMember("이름1");
        issueMemberService.create(member);
    }

    @DisplayName("댓글 만들기")
    @Order(2)
    @Test
    void createIssueComment() throws Exception {
        IssueMember issueMember = issueMemberService.toEntity(1L);
        DefaultIssue issue = defaultIssueService.toEntity(1L);
        String url = "/api/v1/comment";
        IssueComment comment = IssueComment.builder().author(issueMember).issue(issue).comment("아 그것참").build();
        MvcResult response = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(comment))
                )
                .andReturn();
        assertEquals(201, response.getResponse().getStatus());
    }

    @DisplayName("댓글 두개 이상 예외")
    @Order(3)
    @Test
    void cannotCreateIssueComment() throws Exception {
        IssueMember issueMember = issueMemberService.toEntity(1L);
        DefaultIssue issue = defaultIssueService.toEntity(1L);
        String url = "/api/v1/comment";
        IssueComment comment = IssueComment.builder().author(issueMember).issue(issue).comment("아 그것참222").build();
        MvcResult response = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(comment))
                )
                .andReturn();
        assertEquals(409, response.getResponse().getStatus());
    }

    @DisplayName("댓글 업데이트")
    @Order(4)
    @Test
    void updateIssueComment() throws Exception {
        IssueComment comment = issueCommentService.toEntity(1L);
        String url = "/api/v1/comment";
        IssueComment updateComment = IssueComment.builder().id(comment.getId()).author(comment.getAuthor()).issue(comment.getIssue()).comment("아 그것참222").build();
        MvcResult response = mockMvc
                .perform(patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(updateComment))
                )
                .andReturn();
        System.out.println(response.getResponse().getContentAsString());
        IssueComment updated = new ObjectMapper().readValue(response.getResponse().getContentAsString(), IssueComment.class);
        assertEquals(200, response.getResponse().getStatus());
        assertEquals("아 그것참222", updated.getComment());
    }

//    @DisplayName("댓글 업데이트 두번 이상 예외")
//    @Test
//    void cannotUpdateIssueComment() {
//        IssueComment issueComment = createIssueComment();
//
//        String url = "/api/v1/comment";
//        issueCommentService.updateComment(issueComment, "짜라짜라");
//        HttpEntity<IssueComment> request = new HttpEntity<>(issueComment);
//        restTemplate.exchange(url, HttpMethod.PATCH, request, IssueComment.class);
//
//        issueCommentService.updateComment(issueComment, "짜라짜라2");
//        HttpEntity<IssueComment> request2 = new HttpEntity<>(issueComment);
//        ResponseEntity<IssueComment> response = restTemplate.exchange(url, HttpMethod.PATCH, request2, IssueComment.class);
//        assertEquals(405, response.getStatusCodeValue());
//    }
//
//    @DisplayName("댓글 삭제")
//    @Test
//    void deleteIssueComment() {
//        IssueComment issueComment = createIssueComment();
//        String url = "/api/v1/comment";
//        HttpEntity<IssueComment> request = new HttpEntity<>(issueComment);
//        ResponseEntity<IssueComment> response = restTemplate.exchange(url, HttpMethod.DELETE, request, IssueComment.class);
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("노래노래노래노래", Objects.requireNonNull(response.getBody()).getComment());
//    }
//
//    @DisplayName("댓글 Author 아님 삭제 예외")
//    @Test
//    void cannnotDeleteIssueCommentByNotAuthor() {
//        IssueComment issueComment = createIssueComment();
//        String url = "/api/v1/comment";
//        HttpEntity<IssueComment> request = new HttpEntity<>(issueComment);
//        ResponseEntity<IssueComment> response = restTemplate.exchange(url, HttpMethod.DELETE, request, IssueComment.class);
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals("노래노래노래노래", Objects.requireNonNull(response.getBody()).getComment());
//    }
}
