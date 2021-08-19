package com.cgg.service.pay.dto.command;

import com.cgg.framework.dto.request.Command;
import com.cgg.framework.ensure.Ensure;
import com.cgg.framework.validate.Validate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.server.reactive.ServerHttpRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CallbackCmd extends Command implements Validate {

    private String payGate; //回调网关
    private ServerHttpRequest request; //回调数据

    @Override
    public void validate() {
        Ensure.paramNotBlank(payGate, "网关为空");
    }
}
