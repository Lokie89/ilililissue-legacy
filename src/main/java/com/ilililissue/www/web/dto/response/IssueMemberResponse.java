package com.ilililissue.www.web.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/06
 */
@Getter
public class IssueMemberResponse extends BaseTimeResponse {
    @ApiModelProperty(example = "1")
    private long id;
    @ApiModelProperty(value = "이름", example = "홍길동")
    private String name;
}
