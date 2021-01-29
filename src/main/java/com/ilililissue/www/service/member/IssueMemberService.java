package com.ilililissue.www.service.member;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.domain.member.IssueMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueMemberService {

    private final IssueMemberRepository repository;

    public IssueMemberService(IssueMemberRepository repository) {
        this.repository = repository;
    }

    public void create(IssueMember issueMember) {
        repository.save(issueMember);
    }

    public List<IssueMember> getAll() {
        return repository.findAll();
    }
}
