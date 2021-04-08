package com.ilililissue.www.web.dto.response;

import com.ilililissue.www.domain.comment.IssueComment;
import com.ilililissue.www.domain.member.IssueMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/06
 */
@Getter
public class CommentLikeResponse {

    @ApiModelProperty(value = "좋아요/취소 한 멤버")
    private IssueMember member;
    @ApiModelProperty(value = "좋아요/취소 한 댓글")
    private IssueComment comment;
    @ApiModelProperty(value = "상태", example = "true")
    private boolean status;

}
