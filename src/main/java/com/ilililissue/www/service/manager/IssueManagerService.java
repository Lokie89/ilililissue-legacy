package com.ilililissue.www.service.manager;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.IssueManagerRepository;
import com.ilililissue.www.exception.NoContentFromRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IssueManagerService {

    private final IssueManagerRepository repository;

    @Transactional
    public IssueManager create(IssueManager issueManager) {
        return repository.save(issueManager);
    }

    public List<IssueManager> getAll() {
        return repository.findAll();
    }

    public IssueManager getOneById(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }
}
