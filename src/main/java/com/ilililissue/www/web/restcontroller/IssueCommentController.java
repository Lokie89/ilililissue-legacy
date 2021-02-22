package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.like.CommentLikeService;
import com.ilililissue.www.web.dto.request.IssueCommentDeleteDto;
import com.ilililissue.www.web.dto.request.IssueCommentSaveDto;
import com.ilililissue.www.web.dto.response.IssueCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/comments")
@RestController
public class IssueCommentController {

    private final IssueCommentService issueCommentService;
    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseEntity<IssueCommentResponseDto> createIssueComment(@RequestBody IssueCommentSaveDto issueCommentSaveDto) {
        Long authorId = 1L;
        IssueComment savedIssueComment = issueCommentService.create(issueCommentSaveDto.toEntity(authorId));
        return new ResponseEntity<>(new IssueCommentResponseDto(savedIssueComment), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<IssueCommentResponseDto> updateCommentIssueComment(@RequestBody IssueComment issueComment) {
        String updateComment = issueComment.getComment();
        IssueComment entityIssueComment = issueCommentService.getOneById(issueComment.getId());
        IssueComment updatedIssueComment = issueCommentService.updateComment(entityIssueComment, updateComment);
        return new ResponseEntity<>(new IssueCommentResponseDto(updatedIssueComment), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<IssueCommentResponseDto> deleteIssueComment(@RequestBody IssueCommentDeleteDto issueCommentDeleteDto) {
        IssueMember author = issueCommentDeleteDto.getAuthor();
        IssueComment issueComment = issueCommentDeleteDto.getIssueComment();
        issueCommentService.remove(issueComment, author);
        return new ResponseEntity<>(new IssueCommentResponseDto(issueComment), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<IssueCommentResponseDto>> getIssueCommentList() {
        List<IssueComment> issueCommentList = issueCommentService.getAll();
        List<IssueCommentResponseDto> issueCommentResponseDtoList
                = issueCommentList.stream()
                .map(issueComment -> {
                    IssueCommentResponseDto responseDto = new IssueCommentResponseDto(issueComment);
                    responseDto.setCommentLikeList(commentLikeService.getCommentLikeListByIssueComment(issueComment));
                    return responseDto;
                }).collect(Collectors.toList())
                ;
        return new ResponseEntity<>(issueCommentResponseDtoList, HttpStatus.OK);
    }
}
