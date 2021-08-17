package com.cgg.service.user.controller.v1.parammodel;

import com.cgg.framework.dto.request.Command;
import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.validate.Validate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "发送登录校验码参数")
public class SendLoginVerifyCodePM extends Command implements Validate {

    private String phone;

    @Override
    public void validate() {
        Ensure.paramNotBlank(phone, "手机号为空");
    }
}
