package com.ilililissue.www.domain.comment;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class IssueComment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ISSUEMEMBER_ID")
    private IssueMember member;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DEFAULTISSUE_ID")
    private DefaultIssue issue;

    @Column(name = "comment")
    private String comment;

}
