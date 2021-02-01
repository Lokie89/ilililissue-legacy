package com.ilililissue.www.service.member;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.domain.member.IssueMemberRepository;
import com.ilililissue.www.exception.NoContentFromRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IssueMemberService {

    private final IssueMemberRepository repository;

    public IssueMember create(IssueMember issueMember) {
        return repository.save(issueMember);
    }

    public List<IssueMember> getAll() {
        return repository.findAll();
    }

    public IssueMember getById(Long authorId) {
        Optional<IssueMember> savedIssueMember = repository.findById(authorId);
        if(savedIssueMember.isPresent()){
            return savedIssueMember.get();
        }
        throw new NoContentFromRequestException();
    }
}
