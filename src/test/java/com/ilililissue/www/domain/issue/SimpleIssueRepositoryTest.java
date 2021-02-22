package com.ilililissue.www.domain.issue;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.IssueManagerRepository;
import com.ilililissue.www.domain.manager.ManagerRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("이슈 저장소 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties")
public class SimpleIssueRepositoryTest {
    @Autowired
    SimpleIssueRepository repository;

    @Autowired
    IssueManagerRepository managerRepository;

    @DisplayName("이슈 저장소에 저장")
    @Test
    void saveIssueTest() {
        IssueManager manager = managerRepository.save(new IssueManager(ManagerRole.MASTER));
        SimpleIssue simpleIssue = SimpleIssue.builder().creator(manager).title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images(new String[]{"image", "image2"}).description("내용").build();
        repository.save(simpleIssue);
        List<SimpleIssue> simpleIssueList = repository.findAll();
        SimpleIssue savedIssue = simpleIssueList.get(0);
        IssueManager issueManager = savedIssue.getCreator();
        assertEquals("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어", savedIssue.getTitle());
        assertEquals("MASTER", issueManager.getRole().name());
    }
}