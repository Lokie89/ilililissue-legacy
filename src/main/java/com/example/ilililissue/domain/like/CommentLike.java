package com.example.ilililissue.domain.like;

import com.example.ilililissue.domain.comment.IssueComment;
import com.example.ilililissue.domain.member.IssueMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class CommentLike implements Like {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "ISSUEMEMBER_ID")
    private IssueMember member;

    @ManyToOne
    @JoinColumn(name = "ISSUECOMMENT_ID")
    private IssueComment comment;

    @Override
    public int createLike() {
        return 1;
    }
}
