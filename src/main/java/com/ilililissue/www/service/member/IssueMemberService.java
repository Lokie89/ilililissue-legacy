package com.ilililissue.www.service.member;

import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.domain.member.IssueMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueMemberService {

    private IssueMemberRepository issueMemberRepository;

    public IssueMemberService(IssueMemberRepository issueMemberRepository) {
        this.issueMemberRepository = issueMemberRepository;
    }

    public void create(IssueMember issueMember) {
        issueMemberRepository.save(issueMember);
    }

    public List<IssueMember> getAll() {
        return issueMemberRepository.findAll();
    }
}
