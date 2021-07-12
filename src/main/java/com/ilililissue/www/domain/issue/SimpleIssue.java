package com.ilililissue.www.domain.issue;

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
public class SimpleIssue {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ISSUEMEMBER_ID", nullable = false)
    private IssueMember creator;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "images")
    private String[] images;

    @Column(name = "description")
    private String description;

    public SimpleIssue(IssueMember creator, String title, String[] images, String description) {
        this.creator = creator;
        this.title = title;
        this.images = images;
        this.description = description;
    }

}
