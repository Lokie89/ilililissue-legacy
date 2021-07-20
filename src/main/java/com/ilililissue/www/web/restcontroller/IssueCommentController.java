package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.comment.IssueCommentService;
import com.ilililissue.www.service.like.CommentLikeService;
import com.ilililissue.www.service.member.IssueMemberService;
import com.ilililissue.www.web.dto.request.comment.IssueCommentSaveRequest;
import com.ilililissue.www.web.dto.request.comment.IssueCommentPatchRequest;
import com.ilililissue.www.web.dto.response.IssueCommentResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/comments")
@RestController
public class IssueCommentController {

    private final IssueCommentService issueCommentService;
    private final IssueMemberService issueMemberService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<IssueCommentResponse> createIssueComment(@Valid @RequestBody IssueCommentSaveRequest saveRequest, Authentication authentication) {
        String memberName = authentication.getName();
        IssueMember author = issueMemberService.getOneByName(memberName);
        saveRequest.setAuthor(author);
        IssueComment savedIssueComment = issueCommentService.create(toEntityForSave(saveRequest));
        return new ResponseEntity<>(toResponseDto(savedIssueComment), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<IssueCommentResponse> updateCommentIssueComment(@Valid @RequestBody IssueCommentPatchRequest patchRequest, Authentication authentication) {
        IssueMember author = issueCommentService.getOneById(patchRequest.getId()).getAuthor();
        if (!author.equals(issueMemberService.getOneByName(authentication.getName()))) {
            return ResponseEntity.badRequest().build();
        }
        IssueComment updatedIssueComment = issueCommentService.patch(toEntityForPatch(patchRequest));
        return new ResponseEntity<>(toResponseDto(updatedIssueComment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteIssueComment(@PathVariable Long id) {
        issueCommentService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<IssueCommentResponse>> getIssueCommentList() {
        List<IssueComment> issueCommentList = issueCommentService.getAll();
        List<IssueCommentResponse> issueCommentResponseList
                = issueCommentList.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList())
                ;
        return new ResponseEntity<>(issueCommentResponseList, HttpStatus.OK);
    }

    private IssueCommentResponse toResponseDto(IssueComment entity) {
        return modelMapper.map(entity, IssueCommentResponse.class);
    }

    private IssueComment toEntityForSave(IssueCommentSaveRequest request) {
        return modelMapper.map(request, IssueComment.class);
    }

    private IssueComment toEntityForPatch(IssueCommentPatchRequest request) {
        return modelMapper.map(request, IssueComment.class);
    }

}
