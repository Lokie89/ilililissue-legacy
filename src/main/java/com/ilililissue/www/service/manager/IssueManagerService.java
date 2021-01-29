package com.ilililissue.www.service.manager;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.IssueManagerRepository;
import com.ilililissue.www.exception.NoContentFromRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueManagerService {

    private final IssueManagerRepository repository;

    public IssueManagerService(IssueManagerRepository repository) {
        this.repository = repository;
    }

    public IssueManager create(IssueManager issueManager) {
        return repository.save(issueManager);
    }

    public List<IssueManager> getAll() {
        return repository.findAll();
    }

    public IssueManager getById(Long id) {
        Optional<IssueManager> optionalIssueManager = repository.findById(id);
        if (optionalIssueManager.isPresent()) {
            return optionalIssueManager.get();
        }
        throw new NoContentFromRequestException();
    }

    public IssueManager getById(IssueManager notPersistIssueManager) {
        return getById(notPersistIssueManager.getId());
    }
}
