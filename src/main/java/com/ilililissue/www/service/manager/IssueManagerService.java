package com.ilililissue.www.service.manager;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.IssueManagerRepository;
import com.ilililissue.www.exception.NoContentFromRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueManagerService {

    private IssueManagerRepository issueManagerRepository;

    public IssueManagerService(IssueManagerRepository issueManagerRepository) {
        this.issueManagerRepository = issueManagerRepository;
    }

    public IssueManager create(IssueManager issueManager) {
        return issueManagerRepository.save(issueManager);
    }

    public List<IssueManager> getAll() {
        return issueManagerRepository.findAll();
    }

    public IssueManager getById(Long id) {
        Optional<IssueManager> optionalIssueManager = issueManagerRepository.findById(id);
        if (optionalIssueManager.isPresent()) {
            return optionalIssueManager.get();
        }
        throw new NoContentFromRequestException();
    }
}
