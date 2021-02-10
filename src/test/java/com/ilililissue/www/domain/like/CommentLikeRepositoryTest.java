package com.ilililissue.www.domain.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.comment.IssueCommentRepository;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.issue.DefaultIssueRepository;
import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.IssueManagerRepository;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.domain.member.IssueMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("좋아요 저장소 테스트")
@Transactional
@SpringBootTest(properties = "application-test.properties")
public class CommentLikeRepositoryTest {
    @Autowired
    CommentLikeRepository repository;

    @Autowired
    IssueMemberRepository memberRepository;

    @Autowired
    IssueManagerRepository managerRepository;

    @Autowired
    DefaultIssueRepository issueRepository;

    @Autowired
    IssueCommentRepository commentRepository;

    @DisplayName("좋아요 저장소 저장")
    @Test
    void saveCommentLikeTest() {
        IssueMember member = memberRepository.save(IssueMember.builder().name("회원1").build());
        IssueManager manager = managerRepository.save(new IssueManager(ManagerRole.MASTER));
        DefaultIssue defaultIssue = issueRepository.save(DefaultIssue.builder().creator(manager).title("애플도 VR을?… “고성능·고가형 헤드셋 개발 중”").images(new String[]{"apple1", "vr1"}).description("페이스북 자회사 오큘러스의 VR 헤드셋 '오큘러스 퀘스트2'. 애플이 개발 중인 헤드셋도 이와 유사할 것으로 예측된다.").build());
        IssueComment issueComment = commentRepository.save(IssueComment.builder().author(member).issue(defaultIssue).comment("저건 얼마나 비쌀라나??").build());
        IssueMember likeMember = memberRepository.save(IssueMember.builder().name("회원1의댓글을좋아하는회원2").build());
        CommentLike commentLike = CommentLike.builder().member(likeMember).comment(issueComment).build();
        repository.save(commentLike);
        List<CommentLike> commentLikeList = repository.findAll();

        CommentLike savedCommentLike = commentLikeList.get(0);
        IssueMember commentLikeMember = savedCommentLike.getMember();
        assertEquals("회원1의댓글을좋아하는회원2", commentLikeMember.getName());
        IssueComment likedIssueComment = savedCommentLike.getComment();
        assertEquals("회원1", likedIssueComment.getAuthor().getName());
        assertEquals("저건 얼마나 비쌀라나??", likedIssueComment.getComment());

        DefaultIssue likedCommentedIssue = likedIssueComment.getIssue();
        assertEquals("페이스북 자회사 오큘러스의 VR 헤드셋 '오큘러스 퀘스트2'. 애플이 개발 중인 헤드셋도 이와 유사할 것으로 예측된다.", likedCommentedIssue.getDescription());
        IssueManager commentedIssueManager = likedCommentedIssue.getCreator();
        assertEquals("MASTER", commentedIssueManager.getRole().name());
    }
}
