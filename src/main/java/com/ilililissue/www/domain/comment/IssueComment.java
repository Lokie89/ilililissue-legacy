package com.ilililissue.www.domain.comment;

import com.ilililissue.www.domain.BaseTimeEntity;
import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.manager.UnderControl;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotUpdateCommentException;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class IssueComment extends BaseTimeEntity implements UnderControl {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ISSUEMEMBER_ID")
    private IssueMember author;

    @ManyToOne
    @JoinColumn(name = "DEFAULTISSUE_ID")
    private SimpleIssue issue;

    @Column(name = "comment")
    private String comment;

    @Builder.Default
    @Column(name = "status")
    private char status = 'y';

    @Enumerated
    @Column(name = "position")
    private CommentPosition position;

    public void delete() {
        status = 'n';
    }

    public void drop() {
        status = 'd';
    }

    public boolean isControlled(IssueMember author) {
        return this.author.equals(author);
    }

    public IssueComment updateComment(String comment) {
        if (!getModifiedDate().equals(getCreatedDate())) {
            throw new CanNotUpdateCommentException();
        }
        this.comment = comment;
        return this;
    }

    @Override
    public boolean isControlled(ManagerRole role) {
        return role.isOverAuthorized(ManagerRole.LV3);
    }
}
