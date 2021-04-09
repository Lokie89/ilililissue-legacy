package com.ilililissue.www.web.dto.response;

import com.ilililissue.www.domain.manager.IssueManager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * @author SeongRok.Oh
 * @since 2021/04/06
 */
@Getter
public class SimpleIssueResponse extends BaseTimeResponse {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(value = "작성자")
    private IssueManager creator;
    @ApiModelProperty(value = "제목", example = "오늘의 이슈1")
    private String title;
    @ApiModelProperty(value = "이미지 주소", example = "[\"/image/issue1.jpg\"]")
    private String[] images;
    @ApiModelProperty(value = "설명", example = "오늘의 이슈입니다. 토론해주세요.")
    private String description;
}
