package com.ilililissue.www.web.restcontroller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import com.ilililissue.www.web.dto.request.IssueCommentDeleteRequest;
import com.ilililissue.www.web.dto.request.IssueCommentSaveRequest;
import com.ilililissue.www.web.dto.response.IssueCommentResponse;
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
    SimpleIssueService simpleIssueService;

    @Autowired
    IssueCommentService issueCommentService;

    @Autowired
    WebApplicationContext applicationContext;

    MockMvc mockMvc;

    private final String urlPrefix = "/api/v1/comments";

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
        SimpleIssue issueSaveDto = SimpleIssue.builder().creator(master).title("타이를").build();
        simpleIssueService.create(issueSaveDto);
        IssueMember member = IssueMember.builder().name("이름1").build();
        issueMemberService.create(member);
    }

    @DisplayName("댓글 만들기")
    @Order(2)
    @Test
    void createIssueComment() throws Exception {
        SimpleIssue issue = simpleIssueService.getOneById(1L);
        IssueCommentSaveRequest comment = IssueCommentSaveRequest.builder().issueId(issue.getId()).comment("아 그것참").position("AGREE").build();
        MvcResult response = mockMvc
                .perform(post(urlPrefix)
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
        SimpleIssue issue = simpleIssueService.getOneById(1L);
        IssueCommentSaveRequest comment = IssueCommentSaveRequest.builder().issueId(issue.getId()).comment("아 그것참222").position("DISAGREE").build();
        MvcResult response = mockMvc
                .perform(post(urlPrefix)
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
        IssueComment updateComment = IssueComment.builder().id(comment.getId()).author(comment.getAuthor()).issue(comment.getIssue()).comment("아 그것참222").build();
        MvcResult response = mockMvc
                .perform(patch(urlPrefix)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(updateComment))
                )
                .andReturn();
        IssueCommentResponse updated = new ObjectMapper().readValue(response.getResponse().getContentAsString(), IssueCommentResponse.class);
        assertEquals(200, response.getResponse().getStatus());
        assertEquals("아 그것참222", updated.getComment());
    }

    @DisplayName("댓글 업데이트 두번 이상 예외")
    @Order(5)
    @Test
    void cannotUpdateIssueComment() throws Exception {
        IssueComment comment = issueCommentService.getOneById(1L);
        IssueComment updateComment = IssueComment.builder().id(comment.getId()).author(comment.getAuthor()).issue(comment.getIssue()).comment("아 그것참333").build();
        MvcResult response = mockMvc
                .perform(patch(urlPrefix)
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
        IssueMember author = comment.getAuthor();
        IssueCommentDeleteRequest issueCommentDeleteRequest = IssueCommentDeleteRequest.builder().issueComment(comment).author(author).build();
        MvcResult response = mockMvc
                .perform(delete(urlPrefix)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(issueCommentDeleteRequest))
                )
                .andReturn();
        assertEquals(204, response.getResponse().getStatus());
    }

    @DisplayName("댓글 Author 아님 삭제 예외")
    @Order(6)
    @Test
    void cannotDeleteIssueCommentByNotAuthor() throws Exception {
        IssueComment comment = issueCommentService.getOneById(1L);
        IssueMember author = IssueMember.builder().name("저자아님").build();
        IssueCommentDeleteRequest issueCommentDeleteRequest = IssueCommentDeleteRequest.builder().issueComment(comment).author(author).build();
        MvcResult response = mockMvc
                .perform(delete(urlPrefix)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(issueCommentDeleteRequest))
                )
                .andReturn();
        assertEquals(405, response.getResponse().getStatus());
    }

    @DisplayName("댓글에 좋아요한 숫자")
    @Order(8)
    @Test
    void getIssueCommentLikeTest() throws Exception {
        IssueMember issueMember = issueMemberService.create(IssueMember.builder().name("newMember").build());
        IssueComment issueComment = issueCommentService.create(IssueComment.builder().author(issueMember).issue(issueCommentService.getOneById(1L).getIssue()).comment("new").build());
        IssueMember liker1 = issueMemberService.create(IssueMember.builder().name("라이커").build());
        IssueMember liker2 = issueMemberService.create(IssueMember.builder().name("라이커2").build());
        IssueMember liker3 = issueMemberService.create(IssueMember.builder().name("라이커3").build());
        CommentLike commentLike1 = CommentLike.builder().comment(issueComment).member(liker1).build();
        CommentLike commentLike2 = CommentLike.builder().comment(issueComment).member(liker2).build();
        CommentLike commentLike3 = CommentLike.builder().comment(issueComment).member(liker3).build();
        final String likeUrl = "/api/v1/likes";
        MvcResult response1 = mockMvc
                .perform(post(likeUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(commentLike1))
                )
                .andReturn();
        MvcResult response2 = mockMvc
                .perform(post(likeUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(commentLike2))
                )
                .andReturn();
        MvcResult response3 = mockMvc
                .perform(post(likeUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(commentLike3))
                )
                .andReturn();
        assertEquals(200, response1.getResponse().getStatus());
        assertEquals(200, response2.getResponse().getStatus());
        assertEquals(200, response3.getResponse().getStatus());

        MvcResult response4 = mockMvc
                .perform(get(urlPrefix)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn();
        // TODO : TypeReference 공부
        List<IssueCommentResponse> responseDtoList = new ObjectMapper().readValue(response4.getResponse().getContentAsString(), new TypeReference<>() {});
        IssueCommentResponse responseDto = responseDtoList.stream().filter(issueCommentResponse -> issueCommentResponse.getId().equals(issueComment.getId())).findFirst().get();
        assertEquals(3, responseDto.getCommentLikeList().stream().filter(commentLike -> commentLike.getStatus() == 'y').count());
    }
}
