package com.ilililissue.www.service.issue;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.issue.DefaultIssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {
    private DefaultIssueRepository defaultIssueRepository;

    public IssueService(DefaultIssueRepository defaultIssueRepository) {
        this.defaultIssueRepository = defaultIssueRepository;
    }

    public void create(DefaultIssue defaultIssue) {
        defaultIssueRepository.save(defaultIssue);
    }

    public List<DefaultIssue> getAll() {
        return defaultIssueRepository.findAll();
    }
}
