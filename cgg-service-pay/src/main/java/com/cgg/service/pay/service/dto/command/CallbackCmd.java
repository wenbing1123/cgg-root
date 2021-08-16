package com.cgg.service.pay.service.dto.command;

import com.cgg.framework.dto.request.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.server.reactive.ServerHttpRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CallbackCmd extends Command {

    private String payGate; //回调网关
    private ServerHttpRequest request; //回调数据

}
