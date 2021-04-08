package com.ilililissue.www.domain.like;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
//@IdClass(CommentLikeId.class) // TODO : IssueMember, IssueComment 복합키로 아이디값 처리
@Entity
public class CommentLike implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @CreatedBy
    @OneToOne
    @JoinColumn(name = "ISSUEMEMBER_ID")
    private IssueMember member;

    @OneToOne
    @JoinColumn(name = "ISSUECOMMENT_ID")
    private IssueComment comment;

    @Column(name = "STATUS")
    private Boolean status;

}
