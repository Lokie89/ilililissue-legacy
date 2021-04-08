package com.ilililissue.www.web.dto.request.manager;

import com.ilililissue.www.domain.manager.ManagerRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author SeongRok.Oh
 * @since 2021/04/06
 */
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class IssueManagerSaveRequest {
    @ApiModelProperty(value = "역할, 권한", example = "MASTER", required = true)
    private ManagerRole role;
}
