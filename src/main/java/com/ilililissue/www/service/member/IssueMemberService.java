package com.ilililissue.www.service.member;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.domain.member.IssueMemberRepository;
import com.ilililissue.www.exception.CanNotBecomeEntityException;
import com.ilililissue.www.exception.NoContentFromRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public IssueMember toEntity(Long authorId) {
        return repository.findById(authorId).orElseThrow(NoContentFromRequestException::new);
    }

    public IssueMember toEntity(IssueMember notPersistIssueMember){
        if (Objects.isNull(notPersistIssueMember.getId())) {
            throw new CanNotBecomeEntityException();
        }
        return toEntity(notPersistIssueMember.getId());
    }
}
