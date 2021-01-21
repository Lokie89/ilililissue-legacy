package com.example.ilililissue.domain.issue;

import com.example.ilililissue.domain.manager.Manager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultIssue implements Issue {

    private final Manager creator;
    private final String title;
    private final String[] images;
    private final String description;

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
        private String[] images;
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
