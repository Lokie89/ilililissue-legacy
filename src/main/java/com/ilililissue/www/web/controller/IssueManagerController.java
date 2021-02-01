package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.service.manager.IssueManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/issue/manager")
@RestController
public class IssueManagerController {

    private final IssueManagerService issueManagerService;

    @PostMapping(value = "")
    public ResponseEntity<IssueManager> createIssueManager(@RequestBody IssueManager issueManager) {
        IssueManager savedIssueManager = issueManagerService.create(issueManager);
        return new ResponseEntity<>(savedIssueManager, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<IssueManager> getIssueManager(@PathVariable("id") Long id) {
        System.out.println(id);
        return new ResponseEntity<>(issueManagerService.toEntity(id), HttpStatus.OK);
    }
}
