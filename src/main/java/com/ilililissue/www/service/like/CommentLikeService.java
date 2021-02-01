package com.ilililissue.www.service.like;

import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.like.CommentLikeRepository;
import com.ilililissue.www.exception.CanNotBecomeEntityException;
import com.ilililissue.www.exception.NoContentFromRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentLikeService {

    private final CommentLikeRepository repository;

    private void create(CommentLike commentLike) {
        repository.save(commentLike);
    }

    public void createOrCancel(CommentLike commentLike) {
        if (existCommentLike(commentLike)) {
            CommentLike persistCommentLike = getOneByCommentAndMember(commentLike);
            persistCommentLike.likeOrCancel();
            return;
        }
        create(commentLike);
    }

    private boolean existCommentLike(CommentLike commentLike) {
        return repository.existsByCommentAndMember(commentLike.getComment(), commentLike.getMember());
    }

    private CommentLike getOneByCommentAndMember(CommentLike commentLike) {
        return repository.findByCommentAndMember(commentLike.getComment(), commentLike.getMember());
    }

    public CommentLike toEntity(Long id){
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

    public CommentLike toEntity(CommentLike notPersistCommentLike){
        if (Objects.isNull(notPersistCommentLike.getId())) {
            throw new CanNotBecomeEntityException();
        }
        return toEntity(notPersistCommentLike.getId());
    }

    public List<CommentLike> getAll() {
        return repository.findAll();
    }
}
