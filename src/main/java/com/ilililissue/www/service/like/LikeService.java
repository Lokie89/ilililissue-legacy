package com.ilililissue.www.service.like;

import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.like.CommentLikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {


    private CommentLikeRepository commentLikeRepository;

    public LikeService(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    private void create(CommentLike commentLike) {
        commentLikeRepository.save(commentLike);
    }

    public void createOrCancel(CommentLike commentLike) {
        if (existCommentLike(commentLike)) {
            if (commentLike.isCanceled()) {
                commentLike.reLike();
                return;
            }
            commentLike.cancel();
            return;
        }
        create(commentLike);
    }

    private boolean existCommentLike(CommentLike commentLike) {
        return commentLikeRepository.existsByCommentAndMember(commentLike.getComment(), commentLike.getMember());
    }

    public List<CommentLike> getAll() {
        return commentLikeRepository.findAll();
    }
}
