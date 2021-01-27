package com.ilililissue.www.domain.comment;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.member.IssueMember;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Setter
    @Column(name = "comment")
    private String comment;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
