package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("댓글 컨트롤러 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueCommentControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    IssueCommentService issueCommentService;

    private IssueManager createAndGetManager(ManagerRole role) {
        String url = "/api/v1/issue/manager";
        IssueManager issueManager = new IssueManager(role);
        HttpEntity<IssueManager> entity = new HttpEntity<>(issueManager);
        ResponseEntity<IssueManager> response = restTemplate.postForEntity(url, entity, IssueManager.class);
        return response.getBody();
    }

    private DefaultIssue createAndGetDefaultIssue() {
        IssueManager master = createAndGetManager(ManagerRole.MASTER);
        String url = "/api/v1/issue";
        HttpEntity<DefaultIssueSaveDto> request = new HttpEntity<>(DefaultIssueSaveDto.builder().creator(master).title("이슈 제목").images("이미지1", "이미지2").description("반갑습니다.").build());
        ResponseEntity<DefaultIssue> response = restTemplate.postForEntity(url, request, DefaultIssue.class);
        return response.getBody();
    }

    private IssueMember createAndGetIssueMember() {
        String url = "/api/v1/issue/member";
        IssueMember member = new IssueMember("댓글러");
        HttpEntity<IssueMember> request = new HttpEntity<>(member);
        ResponseEntity<IssueMember> response = restTemplate.postForEntity(url, request, IssueMember.class);
        return response.getBody();
    }

    @DisplayName("댓글 만들기")
    @Test
    IssueComment createIssueComment() {
        DefaultIssue issue = createAndGetDefaultIssue();
        IssueMember issueMember = createAndGetIssueMember();
        String url = "/api/v1/issue/comment";
        HttpEntity<IssueComment> request = new HttpEntity<>(IssueComment.builder().author(issueMember).issue(issue).comment("노래노래노래노래").build());
        ResponseEntity<IssueComment> response = restTemplate.postForEntity(url, request, IssueComment.class);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("노래노래노래노래", Objects.requireNonNull(response.getBody()).getComment());
        return response.getBody();
    }

    @DisplayName("댓글 두개 이상 예외")
    @Test
    void cannotCreateIssueComment() {
        DefaultIssue issue = createAndGetDefaultIssue();
        IssueMember issueMember = createAndGetIssueMember();
        String url = "/api/v1/issue/comment";
        HttpEntity<IssueComment> request = new HttpEntity<>(IssueComment.builder().author(issueMember).issue(issue).comment("노래노래노래노래").build());
        restTemplate.postForEntity(url, request, IssueComment.class);
        HttpEntity<IssueComment> request2 = new HttpEntity<>(IssueComment.builder().author(issueMember).issue(issue).comment("노래노래노래노래2").build());
        ResponseEntity<IssueComment> response2 = restTemplate.postForEntity(url, request2, IssueComment.class);
        assertEquals(409, response2.getStatusCodeValue());
    }

    @DisplayName("댓글 업데이트")
    @Test
    void updateIssueComment() {
        IssueComment issueComment = createIssueComment();

        String url = "/api/v1/issue/comment";
        issueCommentService.updateComment(issueComment, "짜라짜라");
        HttpEntity<IssueComment> request = new HttpEntity<>(issueComment);
        ResponseEntity<IssueComment> response = restTemplate.exchange(url, HttpMethod.PATCH, request, IssueComment.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("짜라짜라", Objects.requireNonNull(response.getBody()).getComment());
    }

    @DisplayName("댓글 업데이트 두번 이상 예외")
    @Test
    void cannotUpdateIssueComment() {
        IssueComment issueComment = createIssueComment();

        String url = "/api/v1/issue/comment";
        issueCommentService.updateComment(issueComment, "짜라짜라");
        HttpEntity<IssueComment> request = new HttpEntity<>(issueComment);
        restTemplate.exchange(url, HttpMethod.PATCH, request, IssueComment.class);

        issueCommentService.updateComment(issueComment, "짜라짜라2");
        HttpEntity<IssueComment> request2 = new HttpEntity<>(issueComment);
        ResponseEntity<IssueComment> response = restTemplate.exchange(url, HttpMethod.PATCH, request2, IssueComment.class);
        assertEquals(405, response.getStatusCodeValue());
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteIssueComment() {
        IssueComment issueComment = createIssueComment();
        String url = "/api/v1/issue/comment";
        HttpEntity<IssueComment> request = new HttpEntity<>(issueComment);
        ResponseEntity<IssueComment> response = restTemplate.exchange(url, HttpMethod.DELETE, request, IssueComment.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("노래노래노래노래", Objects.requireNonNull(response.getBody()).getComment());
    }

    @DisplayName("댓글 Author 아님 삭제 예외")
    @Test
    void cannnotDeleteIssueCommentByNotAuthor() {
        IssueComment issueComment = createIssueComment();
        String url = "/api/v1/issue/comment";
        HttpEntity<IssueComment> request = new HttpEntity<>(issueComment);
        ResponseEntity<IssueComment> response = restTemplate.exchange(url, HttpMethod.DELETE, request, IssueComment.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("노래노래노래노래", Objects.requireNonNull(response.getBody()).getComment());
    }
}
