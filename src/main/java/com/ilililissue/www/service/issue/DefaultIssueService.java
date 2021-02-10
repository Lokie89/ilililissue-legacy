package com.ilililissue.www.service.issue;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.issue.DefaultIssueRepository;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.exception.NoContentFromRequestException;
import com.ilililissue.www.exception.issue.NotAuthorizedManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultIssueService {
    private final DefaultIssueRepository repository;

    @Transactional
    public DefaultIssue create(DefaultIssue defaultIssue) {
        IssueManager creator = defaultIssue.getCreator();
        if (creator.hasControl(defaultIssue)) {
            return repository.save(defaultIssue);
        }
        throw new NotAuthorizedManagerException();
    }

    public List<DefaultIssue> getAll() {
        return repository.findAll();
    }

    public DefaultIssue getOneById(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

}
