package com.ilililissue.www.domain.comment;

import com.ilililissue.www.domain.BaseTimeEntity;
import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class IssueComment extends BaseTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ISSUEMEMBER_ID")
    private IssueMember author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DEFAULTISSUE_ID")
    private DefaultIssue issue;

    @Setter
    @Column(name = "comment")
    private String comment;

    @Builder.Default
    @Column(name = "status")
    private char status = 'y';

    public void delete() {
        status = 'n';
    }
}
