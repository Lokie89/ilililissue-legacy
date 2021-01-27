package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.comment.IssueCommentRepository;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import com.ilililissue.www.exception.comment.CanNotUpdateCommentException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueCommentService {

    private IssueCommentRepository issueCommentRepository;

    public IssueCommentService(IssueCommentRepository issueCommentRepository) {
        this.issueCommentRepository = issueCommentRepository;
    }

    public void create(IssueComment issueComment) {
        if (exist(issueComment)) {
            throw new CanNotRegisterCommentException();
        }
        issueCommentRepository.save(issueComment);
    }

    private boolean exist(IssueComment issueComment) {
        return issueCommentRepository.existsByIssueAndAuthor(issueComment.getIssue(), issueComment.getAuthor());
    }

    public List<IssueComment> getAll() {
        return issueCommentRepository.findAll();
    }

    public void update(IssueComment issueComment) {
        if (isUpdated(issueComment)) {
            throw new CanNotUpdateCommentException();
        }
    }

    private boolean isUpdated(IssueComment issueComment) {
        return !issueComment.getModifiedDate().equals(issueComment.getCreatedDate());
    }

    public void remove(IssueComment issueComment, IssueMember issueMember) {
        if (!isCommentAuthor(issueComment, issueMember)) {
            throw new CanNotRemoveCommentException();
        }
        issueComment.delete();
    }

    private boolean isCommentAuthor(IssueComment issueComment, IssueMember author) {
        return issueComment.getAuthor().equals(author);
    }

    public void remove(IssueComment issueComment, IssueManager issueManager) {
        if (!issueManager.hasIssueCommentControl()) {
            throw new CanNotRemoveCommentException();
        }
        issueComment.drop();
    }
}
