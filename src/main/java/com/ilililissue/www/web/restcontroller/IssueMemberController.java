package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.member.IssueMemberService;
import com.ilililissue.www.web.dto.request.member.IssueMemberSaveRequest;
import com.ilililissue.www.web.dto.response.IssueMemberResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@RestController
public class IssueMemberController {

    private final IssueMemberService issueMemberService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<IssueMemberResponse> createIssueMember(@Valid @RequestBody IssueMemberSaveRequest saveRequest) {
        IssueMember savedIssueMember = issueMemberService.create(toEntityForSave(saveRequest));
        return new ResponseEntity<>(toResponseDto(savedIssueMember), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<IssueMemberResponse> getIssueMemberById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(toResponseDto(issueMemberService.getOneById(id)), HttpStatus.OK);
    }

    private IssueMemberResponse toResponseDto(IssueMember entity) {
        return modelMapper.map(entity, IssueMemberResponse.class);
    }

    private IssueMember toEntityForSave(IssueMemberSaveRequest request) {
        return modelMapper.map(request, IssueMember.class);
    }
}
