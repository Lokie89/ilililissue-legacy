package com.ilililissue.www.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("회원 저장소 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties")
public class IssueMemberRepositoryTest {

    @Autowired
    IssueMemberRepository issueMemberRepository;

    @DisplayName("멤버 저장소 저장")
    @Test
    void saveMemberTest() {
        IssueMember issueMember = new IssueMember();
        issueMemberRepository.save(issueMember);
        assertEquals(issueMember.getName(), issueMemberRepository.findAll().get(0).getName());
    }
}
