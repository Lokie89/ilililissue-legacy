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

    @OneToOne
    @JoinColumn(name = "ISSUEMEMBER_ID")
    private IssueMember member;

    @ManyToOne
    @JoinColumn(name = "ISSUECOMMENT_ID")
    private IssueComment comment;

    @Builder.Default
    @Column(name = "STATUS")
    private Character status = 'y';

    private boolean isCanceled() {
        return status == 'n';
    }

    private void cancel() {
        status = 'n';
    }

    private void reLike() {
        status = 'y';
    }

    public void likeOrCancel() {
        if(isCanceled()){
            reLike();
            return;
        }
        cancel();
    }
}
