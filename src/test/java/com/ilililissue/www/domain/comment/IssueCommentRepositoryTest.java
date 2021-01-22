package com.ilililissue.www.domain.comment;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.member.IssueMember;
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
public class IssueCommentRepositoryTest {
    @Autowired
    IssueCommentRepository repository;

    @Test
    void saveIssueCommentTest() {
        IssueMember member = new IssueMember("회원1");
        IssueManager manager = new IssueManager("매니저1");
        DefaultIssue defaultIssue = DefaultIssue.builder().creator(manager).title("애플도 VR을?… “고성능·고가형 헤드셋 개발 중”").images("apple1", "vr1").description("페이스북 자회사 오큘러스의 VR 헤드셋 '오큘러스 퀘스트2'. 애플이 개발 중인 헤드셋도 이와 유사할 것으로 예측된다.").build();
        IssueComment issueComment = IssueComment.builder().member(member).issue(defaultIssue).comment("저건 얼마나 비쌀라나??").build();

        repository.save(issueComment);
        List<IssueComment> issueCommentList = repository.findAll();
        IssueComment savedIssueComment = issueCommentList.get(0);
        assertEquals("저건 얼마나 비쌀라나??", savedIssueComment.getComment());
        IssueMember commentMember = savedIssueComment.getMember();
        assertEquals("회원1", commentMember.getName());
        DefaultIssue commentedIssue = savedIssueComment.getIssue();
        assertEquals("애플도 VR을?… “고성능·고가형 헤드셋 개발 중”", commentedIssue.getTitle());
        IssueManager commentedIssueManager = commentedIssue.getCreator();
        assertEquals("매니저1", commentedIssueManager.getRole());
    }
}