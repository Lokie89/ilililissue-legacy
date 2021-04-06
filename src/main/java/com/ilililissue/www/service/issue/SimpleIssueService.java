package com.ilililissue.www.service.issue;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.issue.SimpleIssueRepository;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.exception.NoContentFromRequestException;
import com.ilililissue.www.exception.issue.NotAuthorizedManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class SimpleIssueService {
    private final SimpleIssueRepository repository;

    public SimpleIssue create(SimpleIssue simpleIssue) {
        IssueManager creator = simpleIssue.getCreator();
        if (creator.hasControl(simpleIssue)) {
            return repository.save(simpleIssue);
        }
        throw new NotAuthorizedManagerException();
    }

    @Transactional(readOnly = true)
    public List<SimpleIssue> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public SimpleIssue getOneById(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

}
