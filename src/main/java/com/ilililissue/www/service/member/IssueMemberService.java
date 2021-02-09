package com.ilililissue.www.service.member;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.domain.member.IssueMemberRepository;
import com.ilililissue.www.exception.NoContentFromRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IssueMemberService {

    private final IssueMemberRepository repository;

    @Transactional
    public IssueMember create(IssueMember issueMember) {
        return repository.save(issueMember);
    }

    public List<IssueMember> getAll() {
        return repository.findAll();
    }

    public IssueMember getOneById(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

}
