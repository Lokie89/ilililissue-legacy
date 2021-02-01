package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.comment.IssueCommentRepository;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueCommentService {

    private final IssueCommentRepository repository;

    public IssueCommentService(IssueCommentRepository repository) {
        this.repository = repository;
    }

    public void create(IssueComment issueComment) {
        if (exist(issueComment)) {
            throw new CanNotRegisterCommentException();
        }
        repository.save(issueComment);
    }

    private boolean exist(IssueComment issueComment) {
        return repository.existsByIssueAndAuthor(issueComment.getIssue(), issueComment.getAuthor());
    }

    public List<IssueComment> getAll() {
        return repository.findAll();
    }

    public void updateComment(IssueComment issueComment, String comment) {
        issueComment.updateComment(comment);
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
}
