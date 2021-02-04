package com.ilililissue.www.web.restcontroller;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.service.member.IssueMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/member")
@RestController
public class IssueMemberController {

    private final IssueMemberService issueMemberService;

    @PostMapping(value = "")
    public ResponseEntity<IssueMember> createIssueMember(@RequestBody IssueMember issueMember) {
        IssueMember savedIssueMember = issueMemberService.create(issueMember);
        return new ResponseEntity<>(savedIssueMember, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<IssueMember> getIssueMemberById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(issueMemberService.toEntity(id), HttpStatus.OK);
    }
}
