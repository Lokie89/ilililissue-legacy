package com.ilililissue.www.domain.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("관리자 저장소 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties")
public class IssueManagerRepositoryTest {
    @Autowired
    IssueManagerRepository repository;

    @DisplayName("관리자 저장소 저장")
    @Test
    void saveManagerTest() {
        IssueManager issueManager = new IssueManager(ManagerRole.MASTER);
        repository.save(issueManager);
        List<IssueManager> managerList = repository.findAll();
        assertEquals("MASTER", managerList.get(0).getRole().name());
    }
}
