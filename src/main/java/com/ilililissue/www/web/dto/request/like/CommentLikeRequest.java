package com.ilililissue.www.web.dto.request.like;

import com.ilililissue.www.domain.comment.IssueComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author SeongRok.Oh
 * @since 2021/04/08
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentLikeRequest {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(value = "좋아요/취소 할 댓글", required = true)
    @NotNull
    private IssueComment comment;

    @ApiModelProperty(value = "좋아요/취소", required = true, example = "y")
    @NotNull
    private Boolean status;
}
