package com.ilililissue.www.web.dto.request;

import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleIssueSaveDto {
    private String title;
    private String[] images;
    private String description;

    private SimpleIssueSaveDto(String title, String[] images, String description) {
        this.title = title;
        this.images = images;
        this.description = description;
    }

    public static SimpleIssueSaveDto.Builder builder() {
        return new SimpleIssueSaveDto.Builder();
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

        public SimpleIssueSaveDto.Builder title(String title) {
            this.title = title;
            return this;
        }


        public SimpleIssueSaveDto.Builder images(String... images) {
            this.images = images;
            return this;
        }

        public SimpleIssueSaveDto.Builder description(String description) {
            this.description = description;
            return this;
        }

        public SimpleIssueSaveDto build() {
            return new SimpleIssueSaveDto(title, images, description);
        }
    }

}
