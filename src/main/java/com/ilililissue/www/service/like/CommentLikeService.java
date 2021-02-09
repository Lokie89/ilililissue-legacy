package com.ilililissue.www.service.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.like.CommentLikeRepository;
import com.ilililissue.www.exception.NoContentFromRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentLikeService {

    private final CommentLikeRepository repository;

    private CommentLike create(CommentLike commentLike) {
        return repository.save(commentLike);
    }

    @Transactional
    public CommentLike createOrCancel(CommentLike commentLike) {
        if (existCommentLike(commentLike)) {
            commentLike.likeOrCancel();
            return commentLike;
        }
        return create(commentLike);
    }

    private boolean existCommentLike(CommentLike commentLike) {
        return repository.existsByCommentAndMember(commentLike.getComment(), commentLike.getMember());
    }

    public List<CommentLike> getAll() {
        return repository.findAll();
    }

    public CommentLike getOneById(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

    @Transactional
    public List<CommentLike> getCommentLikeListByIssueComment(IssueComment issueComment){
        return repository.findAllByComment(issueComment);
    }

}
