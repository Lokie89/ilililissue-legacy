package com.ilililissue.www.web.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author SeongRok.Oh
 * @since 2021/04/09
 */
@Getter
public class BaseTimeResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "생성일", example = "2021-04-09T13:23:47.113520")
    private LocalDateTime createdDate;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "수정일", example = "2021-04-09T13:23:47.113520")
    private LocalDateTime modifiedDate;
}
