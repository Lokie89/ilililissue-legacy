package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.like.CommentLikeService;
import com.ilililissue.www.web.dto.request.IssueCommentDeleteRequest;
import com.ilililissue.www.web.dto.request.IssueCommentSaveRequest;
import com.ilililissue.www.web.dto.response.IssueCommentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<IssueCommentResponse> createIssueComment(@RequestBody IssueCommentSaveRequest issueCommentSaveRequest) {
        Long authorId = 1L;
        IssueComment savedIssueComment = issueCommentService.create(issueCommentSaveRequest.toEntity(authorId));
        return new ResponseEntity<>(toResponseDto(savedIssueComment), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<IssueCommentResponse> updateCommentIssueComment(@RequestBody IssueComment issueComment) {
        String updateComment = issueComment.getComment();
        IssueComment entityIssueComment = issueCommentService.getOneById(issueComment.getId());
        IssueComment updatedIssueComment = issueCommentService.updateComment(entityIssueComment, updateComment);
        return new ResponseEntity<>(toResponseDto(updatedIssueComment), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIssueComment(@RequestBody IssueCommentDeleteRequest issueCommentDeleteRequest) {
        IssueMember author = issueCommentDeleteRequest.getAuthor();
        IssueComment issueComment = issueCommentDeleteRequest.getIssueComment();
        issueCommentService.remove(issueComment, author);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<IssueCommentResponse>> getIssueCommentList() {
        List<IssueComment> issueCommentList = issueCommentService.getAll();
        List<IssueCommentResponse> issueCommentResponseList
                = issueCommentList.stream()
                .map(issueComment -> {
                    IssueCommentResponse responseDto = toResponseDto(issueComment);
                    responseDto.setCommentLikeList(commentLikeService.getCommentLikeListByIssueComment(issueComment));
                    return responseDto;
                }).collect(Collectors.toList());
        return new ResponseEntity<>(issueCommentResponseList, HttpStatus.OK);
    }

    private IssueCommentResponse toResponseDto(IssueComment entity) {
        return modelMapper.map(entity, IssueCommentResponse.class);
    }
}
