package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.web.dto.request.SimpleIssueSaveRequest;
import com.ilililissue.www.web.dto.response.SimpleIssueResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/issues")
@RestController
public class SimpleIssueController {

    private final SimpleIssueService simpleIssueService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<SimpleIssueResponse> createIssue(HttpServletRequest request, @RequestBody SimpleIssueSaveRequest issueSaveDto) {
        IssueManager creator = (IssueManager) request.getSession().getAttribute("sign");
        SimpleIssue issue = issueSaveDto.toEntity(creator);
        return new ResponseEntity<>(toResponseDto(simpleIssueService.create(issue)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SimpleIssueResponse> getIssueById(@PathVariable Long id) {
        return new ResponseEntity<>(toResponseDto(simpleIssueService.getOneById(id)), HttpStatus.OK);
    }

    private SimpleIssueResponse toResponseDto(SimpleIssue entity) {
        return modelMapper.map(entity, SimpleIssueResponse.class);
    }
}
