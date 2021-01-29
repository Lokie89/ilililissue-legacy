package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.service.issue.DefaultIssueService;
import com.ilililissue.www.web.dto.DefaultIssueSaveDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/issue")
@RestController
public class IssueController {

    private DefaultIssueService defaultIssueService;

    public IssueController(DefaultIssueService defaultIssueService) {
        this.defaultIssueService = defaultIssueService;
    }

    @PostMapping("")
    public ResponseEntity<DefaultIssue> createIssue(@RequestBody DefaultIssueSaveDto issue) {
        return new ResponseEntity<>(defaultIssueService.create(issue), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultIssue> getIssueById(@PathVariable Long id) {
        return new ResponseEntity<>(defaultIssueService.getById(id), HttpStatus.OK);
    }
}
