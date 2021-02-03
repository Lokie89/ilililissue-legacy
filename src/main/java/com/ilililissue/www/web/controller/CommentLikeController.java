package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.service.like.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/like")
@RestController
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping(value = "")
    public ResponseEntity<CommentLike> createCommentLike(@RequestBody CommentLike commentLike) {
        CommentLike savedCommentLike = commentLikeService.createOrCancel(commentLike);
        return new ResponseEntity<>(savedCommentLike, HttpStatus.CREATED);
    }

}
