package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.like.CommentLikeService;
import com.ilililissue.www.web.dto.request.like.CommentLikeRequest;
import com.ilililissue.www.web.dto.response.CommentLikeResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/likes")
@RestController
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CommentLikeResponse> createOrCancelCommentLike(@Valid @RequestBody CommentLikeRequest request) {
        CommentLike savedCommentLike = commentLikeService.createOrCancel(toEntityForSave(request));
        return new ResponseEntity<>(toResponseDto(savedCommentLike), HttpStatus.OK);
    }

    private CommentLikeResponse toResponseDto(CommentLike commentLike) {
        return modelMapper.map(commentLike, CommentLikeResponse.class);
    }

    private CommentLike toEntityForSave(CommentLikeRequest request) {
        return modelMapper.map(request, CommentLike.class);
    }

}
