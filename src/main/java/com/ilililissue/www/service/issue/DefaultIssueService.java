package com.ilililissue.www.service.issue;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.issue.DefaultIssueRepository;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.exception.NoContentFromRequestException;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DefaultIssueService {
    private final DefaultIssueRepository repository;
    private final IssueManagerService issueManagerService;

    public DefaultIssue create(DefaultIssue defaultIssue) {
        return repository.save(defaultIssue);
    }

    public List<DefaultIssue> getAll() {
        return repository.findAll();
    }

    public DefaultIssue create(DefaultIssueSaveDto defaultIssueSaveDto) {
        IssueManager persistManager = issueManagerService.getById(defaultIssueSaveDto.getCreator());
        DefaultIssue persistIssue = DefaultIssue.builder(persistManager, defaultIssueSaveDto.getTitle()).images(defaultIssueSaveDto.getImages().toArray(String[]::new)).description(defaultIssueSaveDto.getDescription()).build();
        return repository.save(persistIssue);
    }

    public DefaultIssue getById(Long id) {
        Optional<DefaultIssue> optionalDefaultIssue = repository.findById(id);
        if (optionalDefaultIssue.isPresent()) {
            return optionalDefaultIssue.get();
        }
        throw new NoContentFromRequestException();
    }
}
