package com.ilililissue.www.web.dto;

import com.ilililissue.www.domain.issue.DefaultIssue;
import com.ilililissue.www.domain.manager.IssueManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DefaultIssueSaveDto {
    private String title;
    private String[] images;
    private String description;

    private DefaultIssueSaveDto(String title, String[] images, String description) {
        this.title = title;
        this.images = images;
        this.description = description;
    }

    public static DefaultIssueSaveDto.Builder builder() {
        return new DefaultIssueSaveDto.Builder();
    }

    public DefaultIssue toEntity(IssueManager creator) {
        return DefaultIssue.builder(creator, this.title)
                .images(this.images)
                .description(this.description)
                .build()
                ;
    }

    public static class Builder {
        private String title;
        private String[] images;
        private String description;

        public DefaultIssueSaveDto.Builder title(String title) {
            this.title = title;
            return this;
        }


        public DefaultIssueSaveDto.Builder images(String... images) {
            this.images = images;
            return this;
        }

        public DefaultIssueSaveDto.Builder description(String description) {
            this.description = description;
            return this;
        }

        public DefaultIssueSaveDto build() {
            return new DefaultIssueSaveDto(title, images, description);
        }
    }

}
