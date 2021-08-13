package com.cgg.service.user.controller.v1.viewmodel;

import com.cgg.framework.dto.response.ResponseData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(name="个人信息结果")
public class ProfileVM extends ResponseData {

    private String username;

}
