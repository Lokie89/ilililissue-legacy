package com.ilililissue.www.web.dto.request.comment;

import com.ilililissue.www.domain.comment.CommentPosition;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class IssueCommentSaveRequest {
    @ApiModelProperty(value = "이슈 아이디", example = "1", required = true)
    @Min(1)
    private Long issueId;

    @ApiModelProperty(value = "댓글", example = "안녕하세요 반갑습니다.", required = true)
    @NotEmpty
    private String comment;

    @ApiModelProperty(value = "찬반중도", example = "AGREE", required = true)
    @NotEmpty
    private CommentPosition position;
}
