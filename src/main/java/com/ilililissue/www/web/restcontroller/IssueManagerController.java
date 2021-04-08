package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.service.manager.IssueManagerService;
import com.ilililissue.www.web.dto.request.manager.IssueManagerSaveRequest;
import com.ilililissue.www.web.dto.response.IssueManagerResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/managers")
@RestController
public class IssueManagerController {

    private final IssueManagerService issueManagerService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<IssueManagerResponse> createIssueManager(@Valid @RequestBody IssueManagerSaveRequest saveRequest) {
        IssueManager savedIssueManager = issueManagerService.create(toEntityForSave(saveRequest));
        return new ResponseEntity<>(toResponseDto(savedIssueManager), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<IssueManagerResponse> getIssueManager(@PathVariable("id") Long id) {
        return new ResponseEntity<>(toResponseDto(issueManagerService.getOneById(id)), HttpStatus.OK);
    }

    private IssueManagerResponse toResponseDto(IssueManager entity) {
        return modelMapper.map(entity, IssueManagerResponse.class);
    }

    private IssueManager toEntityForSave(IssueManagerSaveRequest request) {
        return modelMapper.map(request, IssueManager.class);
    }
}
