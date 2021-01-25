package com.ilililissue.www.domain.issue;

import com.ilililissue.www.domain.manager.IssueManager;
import com.ilililissue.www.exception.issue.NotAuthorizedManagerException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class DefaultIssue implements Issue {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ISSUEMANAGER_ID", nullable = false)
    private IssueManager creator;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "images")
    @ElementCollection
    private List<String> images;

    @Column(name = "description")
    private String description;

    public DefaultIssue(IssueManager creator, String title, List<String> images, String description) {
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
        if (!creator.hasControl()) {
            throw new NotAuthorizedManagerException();
        }
    }

    public static class Builder {
        private final IssueManager creator;
        private final String title;
        private List<String> images;
        private String description;

        public Builder(IssueManager creator, String title) {
            this.creator = creator;
            this.title = title;
        }

        public Builder images(String... images) {
            this.images = Arrays.asList(images);
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
