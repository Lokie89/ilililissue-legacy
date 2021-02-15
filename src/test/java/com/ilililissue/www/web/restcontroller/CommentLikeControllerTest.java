package com.ilililissue.www.web.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.service.like.CommentLikeService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("좋아요 컨트롤러 테스트")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentLikeControllerTest {

    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    IssueManagerService issueManagerService;

    @Autowired
    SimpleIssueService simpleIssueService;

    @Autowired
    IssueMemberService issueMemberService;

    @Autowired
    IssueCommentService issueCommentService;

    @Autowired
    CommentLikeService commentLikeService;

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
    void createComment() {
        IssueManager master = issueManagerService.create(new IssueManager(ManagerRole.MASTER));
        SimpleIssue issueSaveDto = SimpleIssue.builder().creator(master).title("타이를").build();
        SimpleIssue savedIssue = simpleIssueService.create(issueSaveDto);
        IssueMember member = IssueMember.builder().name("코멘터").build();
        issueMemberService.create(member);
        IssueComment issueComment = IssueComment.builder().issue(savedIssue).author(member).comment("코멘트").build();
        issueCommentService.create(issueComment);
    }

    @DisplayName("좋아요 생성 테스트")
    @Order(2)
    @Test
    void createLikeTest() throws Exception {
        IssueComment issueComment = issueCommentService.getOneById(1L);
        IssueMember liker = issueMemberService.create(IssueMember.builder().name("라이커").build());
        CommentLike commentLike = CommentLike.builder().comment(issueComment).member(liker).build();
        String url = "/api/v1/like";
        MvcResult response = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(commentLike))
                )
                .andReturn();
        assertEquals(200, response.getResponse().getStatus());
        CommentLike savedCommentLike = new ObjectMapper().readValue(response.getResponse().getContentAsString(), CommentLike.class);
        assertEquals("라이커", savedCommentLike.getMember().getName());
    }

    @DisplayName("좋아요 취소 테스트")
    @Order(3)
    @Test
    void cancelLikeTest() throws Exception {
        CommentLike savedCommentLike = commentLikeService.getOneById(1L);
        String url = "/api/v1/like";
        MvcResult response = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(savedCommentLike))
                )
                .andReturn();
        assertEquals(200, response.getResponse().getStatus());
        CommentLike canceledCommentLike = new ObjectMapper().readValue(response.getResponse().getContentAsString(), CommentLike.class);
        assertEquals('n', canceledCommentLike.getStatus());
    }

    @DisplayName("다시 좋아요 테스트")
    @Order(4)
    @Test
    void reLikeTest() throws Exception {
        // TODO : Order(3) 에서 이미 취소했는데 왜 y 로 나오는지
        CommentLike savedCommentLike = commentLikeService.getOneById(1L);
        commentLikeService.createOrCancel(savedCommentLike);
        String url = "/api/v1/like";
        MvcResult response = mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(savedCommentLike))
                )
                .andReturn();
        assertEquals(200, response.getResponse().getStatus());
        CommentLike reLikedCommentLike = new ObjectMapper().readValue(response.getResponse().getContentAsString(), CommentLike.class);
        assertEquals('y', reLikedCommentLike.getStatus());
    }
}
