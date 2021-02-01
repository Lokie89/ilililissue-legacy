package com.ilililissue.www.service.member;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.domain.member.IssueMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IssueMemberService {

    private final IssueMemberRepository repository;

    public void create(IssueMember issueMember) {
        repository.save(issueMember);
    }

    public List<IssueMember> getAll() {
        return repository.findAll();
    }
}
