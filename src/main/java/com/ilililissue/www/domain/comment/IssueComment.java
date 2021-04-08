package com.ilililissue.www.domain.comment;

import com.ilililissue.www.domain.BaseTimeEntity;
import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.manager.UnderControl;
import com.ilililissue.www.domain.member.IssueMember;
import com.ilililissue.www.exception.comment.CanNotUpdateCommentException;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class IssueComment extends BaseTimeEntity implements UnderControl, Serializable {
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

    @Override
    public boolean isControlled(ManagerRole role) {
        return role.isOverAuthorized(ManagerRole.LV3);
    }

    private void validateUpdateComment() {
        if (!getModifiedDate().equals(getCreatedDate())) {
            throw new CanNotUpdateCommentException();
        }
    }

    public void patch(IssueComment patchComment) {
        validateUpdateComment();
        if (Objects.nonNull(patchComment.comment)) {
            this.comment = patchComment.comment;
        }
        if (Objects.nonNull(patchComment.position)) {
            this.position = patchComment.position;
        }
    }
}
