package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.service.issue.IssueService;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/issue")
@RestController
public class IssueController {

    private IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("")
    public ResponseEntity<DefaultIssue> createIssue(@RequestBody DefaultIssueSaveDto issue) {
        return new ResponseEntity<>(issueService.create(issue), HttpStatus.CREATED);
    }
}
