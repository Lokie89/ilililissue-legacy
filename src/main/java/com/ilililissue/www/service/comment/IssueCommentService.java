package com.ilililissue.www.service.comment;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.comment.IssueCommentRepository;
import com.ilililissue.www.exception.NoContentFromRequestException;
import com.ilililissue.www.exception.comment.CanNotRegisterCommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class IssueCommentService {

    private final IssueCommentRepository repository;

    public IssueComment create(IssueComment saveComment) {
        if (exist(saveComment)) {
            throw new CanNotRegisterCommentException();
        }
        return repository.save(saveComment);
    }

    private boolean exist(IssueComment issueComment) {
        return repository.existsByIssueAndAuthor(issueComment.getIssue(), issueComment.getAuthor());
    }

    @Transactional(readOnly = true)
    public List<IssueComment> getAll() {
        return repository.findAll();
    }

    public void remove(long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public IssueComment getOneById(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

    public IssueComment patch(IssueComment patchComment) {
        IssueComment issueComment = getOneById(patchComment.getId());
        issueComment.patch(patchComment);
        return repository.saveAndFlush(issueComment);
    }
}
