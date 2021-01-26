package com.ilililissue.www.service.manager;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.IssueManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueManagerService {

    private IssueManagerRepository issueManagerRepository;

    public IssueManagerService(IssueManagerRepository issueManagerRepository) {
        this.issueManagerRepository = issueManagerRepository;
    }

    public void save(IssueManager issueManager) {
        issueManagerRepository.save(issueManager);
    }

    public List<IssueManager> getAll() {
        return issueManagerRepository.findAll();
    }

}
