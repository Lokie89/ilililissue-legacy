package com.ilililissue.www.domain.issue;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.manager.UnderControl;
import com.ilililissue.www.exception.issue.NotAuthorizedManagerException;
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
public class SimpleIssue implements UnderControl {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ISSUEMANAGER_ID", nullable = false)
    private IssueManager creator;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "images")
    private String[] images;

    @Column(name = "description")
    private String description;

    public SimpleIssue(IssueManager creator, String title, String[] images, String description) {
        this.creator = creator;
        this.title = title;
        this.images = images;
        this.description = description;
    }

    @Override
    public boolean isControlled(ManagerRole role) {
        return role.isOverAuthorized(ManagerRole.LV3);
    }

}
