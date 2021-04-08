package com.ilililissue.www.service.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.like.CommentLikeRepository;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentLikeService {

    private final CommentLikeRepository repository;

    public CommentLike createOrCancel(CommentLike commentLike) {
        return repository.saveAndFlush(commentLike);
    }

    @Transactional(readOnly = true)
    public List<CommentLike> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CommentLike> getCommentLikeListByIssueComment(IssueComment issueComment) {
        return repository.findAllByCommentAndStatus(issueComment, true);
    }

}
