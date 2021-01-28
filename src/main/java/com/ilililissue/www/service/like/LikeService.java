package com.ilililissue.www.service.like;

import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.like.CommentLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {


    private CommentLikeRepository commentLikeRepository;

    public LikeService(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    public void create(CommentLike commentLike) {
        commentLikeRepository.save(commentLike);
    }
}
