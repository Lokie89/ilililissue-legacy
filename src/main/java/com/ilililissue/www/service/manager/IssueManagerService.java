package com.ilililissue.www.service.manager;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.IssueManagerRepository;
import com.ilililissue.www.exception.CanNotBecomeEntityException;
import com.ilililissue.www.exception.NoContentFromRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class IssueManagerService {

    private final IssueManagerRepository repository;

    public IssueManager create(IssueManager issueManager) {
        return repository.save(issueManager);
    }

    public List<IssueManager> getAll() {
        return repository.findAll();
    }

    public IssueManager toEntity(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

    public IssueManager toEntity(IssueManager notPersistIssueManager) {
        if (Objects.isNull(notPersistIssueManager.getId())) {
            throw new CanNotBecomeEntityException();
        }
        return toEntity(notPersistIssueManager.getId());
    }
}
