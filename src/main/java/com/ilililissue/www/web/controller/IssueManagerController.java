package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.service.manager.IssueManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/issue/manager")
@RestController
public class IssueManagerController {

    private IssueManagerService issueManagerService;

    public IssueManagerController(IssueManagerService issueManagerService) {
        this.issueManagerService = issueManagerService;
    }

    @PostMapping(value = "")
    public ResponseEntity<IssueManager> createIssueManager(@RequestBody IssueManager issueManager) {
        IssueManager savedIssueManager = issueManagerService.create(issueManager);
        return new ResponseEntity<>(savedIssueManager, HttpStatus.CREATED);
    }
}
