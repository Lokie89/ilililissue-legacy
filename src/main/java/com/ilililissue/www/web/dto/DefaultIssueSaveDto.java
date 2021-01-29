package com.ilililissue.www.web.dto;

import com.ilililissue.www.domain.manager.IssueManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class DefaultIssueSaveDto {
    private IssueManager creator;
    private String title;
    private List<String> images;
    private String description;

    private DefaultIssueSaveDto(IssueManager creator, String title, List<String> images, String description) {
        this.creator = creator;
        this.title = title;
        this.images = images;
        this.description = description;
    }

    public static DefaultIssueSaveDto.Builder builder() {
        return new DefaultIssueSaveDto.Builder();
    }


    public static class Builder {
        private IssueManager creator;
        private String title;
        private List<String> images;
        private String description;

        public DefaultIssueSaveDto.Builder creator(IssueManager creator) {
            this.creator = creator;
            return this;
        }

        public DefaultIssueSaveDto.Builder title(String title) {
            this.title = title;
            return this;
        }


        public DefaultIssueSaveDto.Builder images(String... images) {
            this.images = Arrays.asList(images);
            return this;
        }

        public DefaultIssueSaveDto.Builder description(String description) {
            this.description = description;
            return this;
        }

        public DefaultIssueSaveDto build() {
            return new DefaultIssueSaveDto(creator, title, images, description);
        }
    }

}
