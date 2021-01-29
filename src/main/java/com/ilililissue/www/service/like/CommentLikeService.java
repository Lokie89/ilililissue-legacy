package com.ilililissue.www.service.like;

import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.like.CommentLikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentLikeService {

    private final CommentLikeRepository repository;

    public CommentLikeService(CommentLikeRepository repository) {
        this.repository = repository;
    }

    private void create(CommentLike commentLike) {
        repository.save(commentLike);
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
        return repository.existsByCommentAndMember(commentLike.getComment(), commentLike.getMember());
    }

    public List<CommentLike> getAll() {
        return repository.findAll();
    }
}
