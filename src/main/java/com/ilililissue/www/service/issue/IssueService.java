package com.ilililissue.www.service.issue;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.issue.DefaultIssueRepository;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {
    private DefaultIssueRepository defaultIssueRepository;
    private IssueManagerService issueManagerService;

    public IssueService(DefaultIssueRepository defaultIssueRepository,
                        IssueManagerService issueManagerService) {
        this.defaultIssueRepository = defaultIssueRepository;
        this.issueManagerService = issueManagerService;
    }

    public DefaultIssue create(DefaultIssue defaultIssue) {
        return defaultIssueRepository.save(defaultIssue);
    }

    public List<DefaultIssue> getAll() {
        return defaultIssueRepository.findAll();
    }

    public DefaultIssue create(DefaultIssueSaveDto defaultIssueSaveDto) {
        IssueManager persistManager = issueManagerService.getById(defaultIssueSaveDto.getCreator());
        DefaultIssue persistIssue = DefaultIssue.builder(persistManager,defaultIssueSaveDto.getTitle()).images(defaultIssueSaveDto.getImages().toArray(String[]::new)).description(defaultIssueSaveDto.getDescription()).build();
        return defaultIssueRepository.save(persistIssue);
    }
}
