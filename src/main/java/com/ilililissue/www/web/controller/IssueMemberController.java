package com.ilililissue.www.web.controller;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.member.IssueMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/issue/member")
@RestController
public class IssueMemberController {

    private final IssueMemberService issueMemberService;

    @PostMapping(value = "")
    public ResponseEntity<IssueMember> createIssueMember(@RequestBody IssueMember issueMember) {
        IssueMember savedIssueMember = issueMemberService.create(issueMember);
        return new ResponseEntity<>(issueMember, HttpStatus.CREATED);
    }
}
