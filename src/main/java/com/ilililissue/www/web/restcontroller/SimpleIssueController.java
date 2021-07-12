package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.issue.SimpleIssueService;
import com.ilililissue.www.web.dto.request.issue.SimpleIssueSaveRequest;
import com.ilililissue.www.web.dto.response.SimpleIssueResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/issues")
@RestController
public class SimpleIssueController {

    private final SimpleIssueService simpleIssueService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<SimpleIssueResponse> createIssue(HttpServletRequest request, @Valid @RequestBody SimpleIssueSaveRequest saveRequest) {
        IssueMember creator = (IssueMember) request.getSession().getAttribute("sign");
        saveRequest.setCreator(creator);
        SimpleIssue issue = toEntityForSave(saveRequest);
        return new ResponseEntity<>(toResponseDto(simpleIssueService.create(issue)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SimpleIssueResponse> getIssueById(@PathVariable Long id) {
        return new ResponseEntity<>(toResponseDto(simpleIssueService.getOneById(id)), HttpStatus.OK);
    }

    private SimpleIssueResponse toResponseDto(SimpleIssue entity) {
        return modelMapper.map(entity, SimpleIssueResponse.class);
    }

    private SimpleIssue toEntityForSave(SimpleIssueSaveRequest request) {
        return modelMapper.map(request, SimpleIssue.class);
    }
}
