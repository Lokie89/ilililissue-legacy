package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.web.dto.request.SimpleIssueSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/issue")
@RestController
public class SimpleIssueController {

    private final SimpleIssueService simpleIssueService;

    @PostMapping("")
    public ResponseEntity<SimpleIssue> createIssue(HttpServletRequest request, @RequestBody SimpleIssueSaveDto issueSaveDto) {
        IssueManager creator = (IssueManager) request.getSession().getAttribute("sign");
        SimpleIssue issue = issueSaveDto.toEntity(creator);
        return new ResponseEntity<>(simpleIssueService.create(issue), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleIssue> getIssueById(@PathVariable Long id) {
        return new ResponseEntity<>(simpleIssueService.getOneById(id), HttpStatus.OK);
    }
}
