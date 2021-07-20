package com.ilililissue.www.web.dto.request.comment;

import com.ilililissue.www.domain.comment.CommentPosition;
import com.ilililissue.www.domain.member.IssueMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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

    @Setter
    @ApiModelProperty(value = "저자", example = "오성록")
    private IssueMember author;

    @ApiModelProperty(value = "댓글", example = "안녕하세요 반갑습니다.", required = true)
    @NotEmpty
    private String comment;

    @ApiModelProperty(value = "찬반중도", example = "AGREE", required = true)
    @NotEmpty
    private CommentPosition position;
}
