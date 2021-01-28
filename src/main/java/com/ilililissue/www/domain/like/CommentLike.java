package com.ilililissue.www.domain.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
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
public class CommentLike {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ISSUEMEMBER_ID")
    private IssueMember member;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ISSUECOMMENT_ID")
    private IssueComment comment;

    @Builder.Default
    @Column(name = "STATUS")
    private Character status = 'y';

    public boolean isCanceled() {
        return status == 'n';
    }

    public void cancel() {
        status = 'n';
    }

    public void reLike() {
        status = 'y';
    }

}
