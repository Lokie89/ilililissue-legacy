package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.service.like.CommentLikeService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.service.member.IssueMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

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

    @Autowired
    ModelMapper modelMapper;

    MockMvc mockMvc;

    private final String urlPrefix = "/api/v1/likes";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .build()
        ;
    }

//    @DisplayName("세팅")
//    @Order(1)
//    @Test
//    void createComment() {
//        IssueManager master = issueManagerService.create(new IssueManager(ManagerRole.MASTER));
//        SimpleIssue issueSaveDto = SimpleIssue.builder().creator(master).title("타이를").build();
//        SimpleIssue savedIssue = simpleIssueService.create(issueSaveDto);
//        IssueMember member = IssueMember.builder().name("코멘터").build();
//        issueMemberService.create(member);
//        IssueComment issueComment = IssueComment.builder().issue(savedIssue).author(member).comment("코멘트").build();
//        issueCommentService.create(issueComment);
//    }

    // TODO : Fix me with Auth
//    @DisplayName("좋아요 생성 테스트")
//    @Order(2)
//    @Test
//    void createLikeTest() throws Exception {
//        IssueComment issueComment = issueCommentService.getOneById(1L);
//        IssueMember liker = issueMemberService.create(IssueMember.builder().name("라이커").build());
//        CommentLikeRequest commentLike = CommentLikeRequest.builder().comment(issueComment).member(liker).build();
//        MvcResult response = mockMvc
//                .perform(post(urlPrefix)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsBytes(commentLike))
//                )
//                .andReturn();
//        assertEquals(200, response.getResponse().getStatus());
//        CommentLikeResponse savedCommentLike = new ObjectMapper().readValue(response.getResponse().getContentAsString(), CommentLikeResponse.class);
//        assertEquals("라이커", savedCommentLike.getMember().getName());
//    }
//
//    @DisplayName("좋아요 취소 테스트")
//    @Order(3)
//    @Test
//    void cancelLikeTest() throws Exception {
//        CommentLike savedCommentLike = commentLikeService.getOneById(1L);
//        MvcResult response = mockMvc
//                .perform(post(urlPrefix)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsBytes(modelMapper.map(savedCommentLike, CommentLikeRequest.class)))
//                )
//                .andReturn();
//        assertEquals(200, response.getResponse().getStatus());
//        CommentLikeResponse canceledCommentLike = new ObjectMapper().readValue(response.getResponse().getContentAsString(), CommentLikeResponse.class);
//        assertEquals('n', canceledCommentLike.getStatus());
//    }
//
//    @DisplayName("다시 좋아요 테스트")
//    @Order(4)
//    @Test
//    void reLikeTest() throws Exception {
//        CommentLike savedCommentLike = commentLikeService.getOneById(1L);
//        commentLikeService.createOrCancel(savedCommentLike);
//        MvcResult response = mockMvc
//                .perform(post(urlPrefix)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsBytes(savedCommentLike))
//                )
//                .andReturn();
//        assertEquals(200, response.getResponse().getStatus());
//        CommentLikeResponse reLikedCommentLike = new ObjectMapper().readValue(response.getResponse().getContentAsString(), CommentLikeResponse.class);
//        assertEquals('y', reLikedCommentLike.getStatus());
//    }
}
