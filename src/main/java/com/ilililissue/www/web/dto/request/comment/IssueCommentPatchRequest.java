package com.ilililissue.www.web.dto.request.comment;

import com.ilililissue.www.domain.comment.CommentPosition;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * @author SeongRok.Oh
 * @since 2021/04/07
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IssueCommentPatchRequest {
    @ApiModelProperty(example = "1")
    @Min(1)
    private Long id;
    @ApiModelProperty(value = "댓글", example = "내용 정정합니다.")
    private String comment;
    @ApiModelProperty(value = "찬반중도", example = "AGREE")
    private CommentPosition position;
}
