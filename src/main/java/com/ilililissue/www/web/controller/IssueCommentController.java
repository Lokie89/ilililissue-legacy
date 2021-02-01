package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.service.comment.IssueCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/issue/comment")
@RestController
public class IssueCommentController {

    private final IssueCommentService issueCommentService;

    @PostMapping(value = "")
    public ResponseEntity<IssueComment> createIssueComment(@RequestBody IssueComment issueComment) {
        IssueComment savedIssueComment = issueCommentService.create(issueComment);
        return new ResponseEntity<>(savedIssueComment, HttpStatus.CREATED);
    }
}
