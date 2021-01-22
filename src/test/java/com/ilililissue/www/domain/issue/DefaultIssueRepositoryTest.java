package com.ilililissue.www.domain.issue;

import com.ilililissue.www.domain.manager.IssueManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "application-test.properties")
public class DefaultIssueRepositoryTest {
    @Autowired
    DefaultIssueRepository repository;

    @Test
    void saveIssueTest() {
        IssueManager manager = new IssueManager("manager");
        DefaultIssue defaultIssue = DefaultIssue.builder().creator(manager).title("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어").images("image", "image2").description("내용").build();
        repository.save(defaultIssue);
        List<DefaultIssue> defaultIssueList = repository.findAll();
        DefaultIssue savedIssue = defaultIssueList.get(0);
        IssueManager issueManager = savedIssue.getCreator();
        assertEquals("신규확진 401명, 이틀째 400명대 초반... 사망자 16명 늘어", savedIssue.getTitle());
        assertEquals("manager", issueManager.getRole());
    }
}