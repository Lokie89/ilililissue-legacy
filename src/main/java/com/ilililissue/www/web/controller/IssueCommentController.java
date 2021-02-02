package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.service.comment.IssueCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/comment")
@RestController
public class IssueCommentController {

    private final IssueCommentService issueCommentService;

    @PostMapping(value = "")
    public ResponseEntity<IssueComment> createIssueComment(@RequestBody IssueComment issueComment) {
        IssueComment savedIssueComment = issueCommentService.create(issueComment);
        return new ResponseEntity<>(savedIssueComment, HttpStatus.CREATED);
    }

    @PatchMapping(value = "")
    public ResponseEntity<IssueComment> updateCommentIssueComment(@RequestBody IssueComment issueComment) {
        String updateComment = issueComment.getComment();
        IssueComment entityIssueComment = issueCommentService.toEntity(issueComment);
        issueCommentService.updateComment(entityIssueComment, updateComment);
        return new ResponseEntity<>(issueCommentService.toEntity(entityIssueComment), HttpStatus.OK);
    }
}
