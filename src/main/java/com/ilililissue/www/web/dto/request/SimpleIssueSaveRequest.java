package com.ilililissue.www.web.dto.request;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleIssueSaveRequest {
    private String title;
    private String[] images;
    private String description;

    private SimpleIssueSaveRequest(String title, String[] images, String description) {
        this.title = title;
        this.images = images;
        this.description = description;
    }

    public static SimpleIssueSaveRequest.Builder builder() {
        return new SimpleIssueSaveRequest.Builder();
    }

    public SimpleIssue toEntity(IssueManager creator) {
        return SimpleIssue.builder()
                .creator(creator)
                .title(this.title)
                .images(this.images)
                .description(this.description)
                .build()
                ;
    }

    public static class Builder {
        private String title;
        private String[] images;
        private String description;

        public SimpleIssueSaveRequest.Builder title(String title) {
            this.title = title;
            return this;
        }


        public SimpleIssueSaveRequest.Builder images(String... images) {
            this.images = images;
            return this;
        }

        public SimpleIssueSaveRequest.Builder description(String description) {
            this.description = description;
            return this;
        }

        public SimpleIssueSaveRequest build() {
            return new SimpleIssueSaveRequest(title, images, description);
        }
    }

}
