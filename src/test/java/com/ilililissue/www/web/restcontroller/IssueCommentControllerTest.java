package com.ilililissue.www.web.restcontroller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.issue.DefaultIssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import com.ilililissue.www.web.dto.request.IssueCommentDeleteDto;
import com.ilililissue.www.web.dto.response.IssueCommentResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


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
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .build()
        ;
    }

    @DisplayName("세팅")
    @Order(1)
    @Test
    void createIssue() {
        IssueManager master = issueManagerService.create(new IssueManager(ManagerRole.MASTER));
        DefaultIssue issueSaveDto = DefaultIssue.builder().creator(master).title("타이를").build();
        defaultIssueService.create(issueSaveDto);
        IssueMember member = new IssueMember("이름1");
        issueMemberService.create(member);
    }

    @DisplayName("댓글 만들기")
    @Order(2)
    @Test
    void createIssueComment() throws Exception {
        IssueMember issueMember = issueMemberService.getOneById(1L);
        DefaultIssue issue = defaultIssueService.getOneById(1L);
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
        IssueMember issueMember = issueMemberService.getOneById(1L);
        DefaultIssue issue = defaultIssueService.getOneById(1L);
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
        IssueComment comment = issueCommentService.getOneById(1L);
        String url = "/api/v1/comment";
        IssueComment updateComment = IssueComment.builder().id(comment.getId()).author(comment.getAuthor()).issue(comment.getIssue()).comment("아 그것참222").build();
        MvcResult response = mockMvc
                .perform(patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(updateComment))
                )
                .andReturn();
        IssueCommentResponseDto updated = new ObjectMapper().readValue(response.getResponse().getContentAsString(), IssueCommentResponseDto.class);
        assertEquals(200, response.getResponse().getStatus());
        assertEquals("아 그것참222", updated.getComment());
    }

    @DisplayName("댓글 업데이트 두번 이상 예외")
    @Order(5)
    @Test
    void cannotUpdateIssueComment() throws Exception {
        IssueComment comment = issueCommentService.getOneById(1L);
        String url = "/api/v1/comment";
        IssueComment updateComment = IssueComment.builder().id(comment.getId()).author(comment.getAuthor()).issue(comment.getIssue()).comment("아 그것참333").build();
        MvcResult response = mockMvc
                .perform(patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(updateComment))
                )
                .andReturn();
        assertEquals(405, response.getResponse().getStatus());
    }

    @DisplayName("댓글 삭제")
    @Order(7)
    @Test
    void deleteIssueComment() throws Exception {
        IssueComment comment = issueCommentService.getOneById(1L);
        IssueMember author = issueMemberService.getOneById(1L);
        IssueCommentDeleteDto issueCommentDeleteDto = IssueCommentDeleteDto.builder().issueComment(comment).author(author).build();
        String url = "/api/v1/comment";
        MvcResult response = mockMvc
                .perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(issueCommentDeleteDto))
                )
                .andReturn();
        assertEquals(200, response.getResponse().getStatus());
    }

    @DisplayName("댓글 Author 아님 삭제 예외")
    @Order(6)
    @Test
    void cannotDeleteIssueCommentByNotAuthor() throws Exception {
        IssueComment comment = issueCommentService.getOneById(1L);
        IssueMember author = new IssueMember("저자아님");
        IssueCommentDeleteDto issueCommentDeleteDto = IssueCommentDeleteDto.builder().issueComment(comment).author(author).build();
        String url = "/api/v1/comment";
        MvcResult response = mockMvc
                .perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(issueCommentDeleteDto))
                )
                .andReturn();
        assertEquals(405, response.getResponse().getStatus());
    }

    @DisplayName("댓글에 좋아요한 숫자")
    @Order(8)
    @Test
    void getIssueCommentLikeTest() throws Exception {
        IssueComment issueComment = issueCommentService.getOneById(1L);
        IssueMember liker1 = issueMemberService.create(new IssueMember("라이커"));
        IssueMember liker2 = issueMemberService.create(new IssueMember("라이커2"));
        IssueMember liker3 = issueMemberService.create(new IssueMember("라이커3"));
        CommentLike commentLike1 = CommentLike.builder().comment(issueComment).member(liker1).build();
        CommentLike commentLike2 = CommentLike.builder().comment(issueComment).member(liker2).build();
        CommentLike commentLike3 = CommentLike.builder().comment(issueComment).member(liker3).build();
        String url = "/api/v1/like";
        MvcResult response1 = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(commentLike1))
                )
                .andReturn();
        MvcResult response2 = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(commentLike2))
                )
                .andReturn();
        MvcResult response3 = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(commentLike3))
                )
                .andReturn();
        assertEquals(200, response1.getResponse().getStatus());
        assertEquals(200, response2.getResponse().getStatus());
        assertEquals(200, response3.getResponse().getStatus());

        String url2 = "/api/v1/comment";

        MvcResult response4 = mockMvc
                .perform(get(url2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        // TODO : TypeReference 공부
        List<IssueCommentResponseDto> responseDtoList = new ObjectMapper().readValue(response4.getResponse().getContentAsString(), new TypeReference<>() {});
        IssueCommentResponseDto responseDto = responseDtoList.get(0);
        assertEquals(3, responseDto.getCommentLikeList().size());
    }
}
