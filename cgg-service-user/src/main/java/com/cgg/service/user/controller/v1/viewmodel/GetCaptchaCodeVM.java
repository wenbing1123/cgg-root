package com.cgg.service.user.controller.v1.viewmodel;

import com.cgg.framework.dto.response.ResponseData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(name="图片验证码结果")
public class GetCaptchaCodeVM extends ResponseData {

    @Schema(description = "session标识")
    private String sessionKey;

    @Schema(description = "图片Base64格式数据")
    private String image;

}
