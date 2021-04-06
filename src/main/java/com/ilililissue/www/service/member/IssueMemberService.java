package com.ilililissue.www.service.member;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.domain.member.IssueMemberRepository;
import com.ilililissue.www.exception.NoContentFromRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class IssueMemberService {

    private final IssueMemberRepository repository;

    public IssueMember create(IssueMember issueMember) {
        return repository.save(issueMember);
    }

    @Transactional(readOnly = true)
    public List<IssueMember> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public IssueMember getOneById(Long id) {
        return repository.findById(id).orElseThrow(NoContentFromRequestException::new);
    }

}
