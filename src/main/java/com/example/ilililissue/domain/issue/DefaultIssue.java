package com.example.ilililissue.domain.issue;

import com.example.ilililissue.domain.manager.Manager;
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

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    private Manager creator;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "images")
    @ElementCollection
    private List<String> images;

    @Column(name = "description")
    private String description;

    public DefaultIssue(Manager creator, String title, List<String> images, String description) {
        this.creator = creator;
        this.title = title;
        this.images = images;
        this.description = description;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int createIssue() {
        return 1;
    }

    public static class Builder {
        private Manager creator;
        private String title;
        private List<String> images;
        private String description;

        public Builder creator(Manager creator) {
            this.creator = creator;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
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
