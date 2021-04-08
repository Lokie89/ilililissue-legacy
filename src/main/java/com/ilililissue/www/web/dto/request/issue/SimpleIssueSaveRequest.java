package com.ilililissue.www.web.dto.request.issue;

import com.ilililissue.www.domain.manager.IssueManager;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SimpleIssueSaveRequest {
    @ApiModelProperty(value = "제목", example = "이슈제목", required = true)
    @NotEmpty
    private String title;

    @ApiModelProperty(value = "이미지주소", example = "[\"/issue/image1.jpg\"]", required = true)
    @NotEmpty
    private String[] images;

    @ApiModelProperty(value = "내용", example = "이슈 내용")
    private String description;

    //TODO : 세터 삭제
    @ApiModelProperty(value = "생성자", required = true)
    @Setter
    private IssueManager creator;
}
