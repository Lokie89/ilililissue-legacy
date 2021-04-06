package com.ilililissue.www.web.dto.response;

import com.ilililissue.www.domain.manager.ManagerRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author SeongRok.Oh
 * @since 2021/04/06
 */
@Getter
public class IssueManagerResponse {
    @ApiModelProperty(example = "1")
    private long id;
    @ApiModelProperty(value = "매니저역할", example = "MASTER")
    private ManagerRole role;
}
