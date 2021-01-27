package com.ilililissue.www.domain.member;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "application-test.properties")
public class IssueMemberRepositoryTest {

    @Autowired
    IssueMemberRepository issueMemberRepository;

    @Test
    void saveMemberTest() {
        IssueMember issueMember = new IssueMember();
        issueMemberRepository.save(issueMember);
        assertEquals(issueMember.getName(), issueMemberRepository.findAll().get(0).getName());
    }
}
