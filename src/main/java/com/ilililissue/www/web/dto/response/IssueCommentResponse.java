package com.ilililissue.www.web.dto.response;

import com.ilililissue.www.domain.comment.CommentPosition;
import com.ilililissue.www.domain.issue.SimpleIssue;
import com.ilililissue.www.domain.like.CommentLike;
import com.ilililissue.www.domain.member.IssueMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class IssueCommentResponse {
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(value = "댓글 작성자")
    private IssueMember author;
    @ApiModelProperty(value = "댓글 달린 이슈")
    private SimpleIssue issue;
    @ApiModelProperty(value = "내용", example = "공감합니다.")
    private String comment;
    @ApiModelProperty(value = "상태", example = "d")
    private char status;
    @ApiModelProperty(value = "찬성중도반대", example = "AGREE")
    private CommentPosition position;
    // TODO : 세터 없애볼것
    @ApiModelProperty(value = "좋아요 리스트")
    @Setter
    private List<CommentLike> commentLikeList;
}
