package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.member.IssueMemberService;
import com.ilililissue.www.web.dto.response.IssueMemberResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/members")
@RestController
public class IssueMemberController {

    private final IssueMemberService issueMemberService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<IssueMemberResponse> createIssueMember(@RequestBody IssueMember issueMember) {
        IssueMember savedIssueMember = issueMemberService.create(issueMember);
        return new ResponseEntity<>(toResponseDto(savedIssueMember), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<IssueMemberResponse> getIssueMemberById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(toResponseDto(issueMemberService.getOneById(id)), HttpStatus.OK);
    }

    private IssueMemberResponse toResponseDto(IssueMember entity){
        return modelMapper.map(entity, IssueMemberResponse.class);
    }
}
