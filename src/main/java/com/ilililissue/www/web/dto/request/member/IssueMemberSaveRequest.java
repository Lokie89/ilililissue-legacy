package com.ilililissue.www.web.dto.request.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author SeongRok.Oh
 * @since 2021/04/06
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class IssueMemberSaveRequest {
    @ApiModelProperty(value = "이름", example = "홍길동", required = true)
    @NotEmpty
    private String name;
}
