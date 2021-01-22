package com.example.ilililissue.domain.comment;

import com.example.ilililissue.domain.issue.DefaultIssue;
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
public class IssueComment implements Comment {
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

    @Override
    public int createComment() {
        return 1;
    }
}
