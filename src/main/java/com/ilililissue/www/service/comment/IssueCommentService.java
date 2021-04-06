package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.comment.IssueCommentRepository;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.NoContentFromRequestException;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class IssueCommentService {

    private final IssueCommentRepository repository;

    public IssueComment create(IssueComment issueComment) {
        if (exist(issueComment)) {
            throw new CanNotRegisterCommentException();
        }
        return repository.save(issueComment);
    }

    private boolean exist(IssueComment issueComment) {
        return repository.existsByIssueAndAuthor(issueComment.getIssue(), issueComment.getAuthor());
    }

    @Transactional(readOnly = true)
    public List<IssueComment> getAll() {
        return repository.findAll();
    }

    public IssueComment updateComment(IssueComment issueComment, String comment) {
        return issueComment.updateComment(comment);
    }

    public void remove(IssueComment issueComment, IssueMember issueMember) {
        if (!issueComment.isControlled(issueMember)) {
            throw new CanNotRemoveCommentException();
        }
        issueComment.delete();
    }

    public void remove(IssueComment issueComment, IssueManager issueManager) {
        if (!issueManager.hasControl(issueComment)) {
            throw new CanNotRemoveCommentException();
        }
        issueComment.drop();
    }

    @Transactional(readOnly = true)
    public IssueComment getOneById(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }
}
