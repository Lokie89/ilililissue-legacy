package com.example.ilililissue.domain.like;

import com.example.ilililissue.domain.comment.IssueComment;
import com.example.ilililissue.domain.member.IssueMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class CommentLike implements Like {

    @OneToOne
    @JoinColumn(name = "ISSUEMEMBER_ID")
    private IssueMember member;
    @OneToMany
    @JoinColumn(name = "ISSUECOMMENT_ID")
    private IssueComment comment;

    @Override
    public int createLike() {
        return 1;
    }
}
