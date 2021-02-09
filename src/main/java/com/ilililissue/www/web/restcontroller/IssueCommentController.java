package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.web.dto.IssueCommentDeleteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        IssueComment updatedIssueComment = issueCommentService.updateComment(issueComment, updateComment);
        return new ResponseEntity<>(updatedIssueComment, HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<IssueComment> deleteIssueComment(@RequestBody IssueCommentDeleteDto issueCommentDeleteDto) {
        IssueMember author = issueCommentDeleteDto.getAuthor();
        IssueComment issueComment = issueCommentDeleteDto.getIssueComment();
        issueCommentService.remove(issueComment, author);
        return new ResponseEntity<>(issueComment, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<IssueComment>> getIssueCommentList() {
        return new ResponseEntity<>(issueCommentService.getAll(), HttpStatus.OK);
    }
}
