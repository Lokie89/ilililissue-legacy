package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.comment.IssueCommentRepository;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import com.ilililissue.www.service.issue.DefaultIssueService;
import com.ilililissue.www.service.member.IssueMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class IssueCommentService {

    private final IssueCommentRepository repository;
    private final IssueMemberService issueMemberService;
    private final DefaultIssueService defaultIssueService;

    private IssueComment persistInnerIssueComment(IssueComment issueComment) {
        IssueMember persistAuthor = null;
        Long authorId = issueComment.getAuthor().getId();
        if (Objects.nonNull(authorId)) {
            persistAuthor = issueMemberService.getById(authorId);
        }

        DefaultIssue persistIssue = null;
        Long issueId = issueComment.getIssue().getId();
        if (Objects.nonNull(issueId)) {
            persistIssue = defaultIssueService.getById(issueId);
        }
        return IssueComment.builder().author(persistAuthor).issue(persistIssue).comment(issueComment.getComment()).build();
    }

    public IssueComment create(IssueComment issueComment) {
        if (exist(issueComment)) {
            throw new CanNotRegisterCommentException();
        }
        return repository.save(persistInnerIssueComment(issueComment));
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
