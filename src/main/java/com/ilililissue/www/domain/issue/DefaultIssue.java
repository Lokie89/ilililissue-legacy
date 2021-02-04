package com.ilililissue.www.domain.issue;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.domain.manager.ManagerRole;
import com.ilililissue.www.domain.manager.UnderControl;
import com.ilililissue.www.exception.issue.NotAuthorizedManagerException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class DefaultIssue implements UnderControl {

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

    public DefaultIssue(IssueManager creator, String title, String[] images, String description) {
        validateCreateIssue(creator);
        this.creator = creator;
        this.title = title;
        this.images = images;
        this.description = description;
    }

    public static Builder builder(IssueManager creator, String title) {
        return new Builder(creator, title);
    }

    private void validateCreateIssue(IssueManager creator) {
        if (!creator.hasControl(this)) {
            throw new NotAuthorizedManagerException();
        }
    }

    @Override
    public boolean isControlled(ManagerRole role) {
        return role.isOverAuthorized(ManagerRole.LV3);
    }

    public static class Builder {
        private final IssueManager creator;
        private final String title;
        private String[] images;
        private String description;

        public Builder(IssueManager creator, String title) {
            this.creator = creator;
            this.title = title;
        }

        public Builder images(String... images) {
            this.images = images;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public DefaultIssue build() {
            return new DefaultIssue(creator, title, images, description);
        }
    }
}
