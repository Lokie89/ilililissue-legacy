package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.comment.IssueCommentRepository;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.CanNotBecomeEntityException;
import com.ilililissue.www.exception.NoContentFromRequestException;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import com.ilililissue.www.exception.comment.CanNotRemoveCommentException;
import com.ilililissue.www.service.issue.DefaultIssueService;
import com.ilililissue.www.service.member.IssueMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class IssueCommentService {

    private final IssueCommentRepository repository;
    private final IssueMemberService issueMemberService;
    private final DefaultIssueService defaultIssueService;

    private IssueComment persistInnerIssueComment(IssueComment issueComment) {
        IssueMember persistAuthor = issueMemberService.toEntity(issueComment.getAuthor());
        DefaultIssue persistIssue = defaultIssueService.toEntity(issueComment.getIssue());
        return IssueComment.builder().author(persistAuthor).issue(persistIssue).comment(issueComment.getComment()).build();
    }

    @Transactional
    public IssueComment create(IssueComment issueComment) {
        IssueComment persistIssueComment = persistInnerIssueComment(issueComment);
        if (exist(persistIssueComment)) {
            throw new CanNotRegisterCommentException();
        }
        return repository.save(persistIssueComment);
    }

    private boolean exist(IssueComment issueComment) {
        return repository.existsByIssueAndAuthor(issueComment.getIssue(), issueComment.getAuthor());
    }

    public List<IssueComment> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void updateComment(IssueComment issueComment, String comment) {
        issueComment.updateComment(comment);
    }

    @Transactional
    public void remove(IssueComment issueComment, IssueMember issueMember) {
        if (!issueComment.isControlled(issueMember)) {
            throw new CanNotRemoveCommentException();
        }
        issueComment.delete();
    }

    public IssueComment toEntity(Long id){
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

    public IssueComment toEntity(IssueComment notPersistIssueComment){
        if (Objects.isNull(notPersistIssueComment.getId())) {
            throw new CanNotBecomeEntityException();
        }
        return toEntity(notPersistIssueComment.getId());
    }

    @Transactional
    public void remove(IssueComment issueComment, IssueManager issueManager) {
        if (!issueManager.hasControl(issueComment)) {
            throw new CanNotRemoveCommentException();
        }
        issueComment.drop();
    }
}
