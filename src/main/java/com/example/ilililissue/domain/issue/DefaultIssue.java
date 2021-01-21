package com.example.ilililissue.domain.issue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultIssue implements Issue {

    private final String title;
    private final String[] images;
    private final String description;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String[] images;
        private String description;

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
            return new DefaultIssue(title, images, description);
        }
    }
}
