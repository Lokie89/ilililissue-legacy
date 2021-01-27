package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.comment.IssueCommentRepository;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
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
        return issueCommentRepository.existsIssueCommentByIssueAndMember(issueComment.getIssue(),issueComment.getMember());
    }

    public List<IssueComment> getAll() {
        return issueCommentRepository.findAll();
    }
}