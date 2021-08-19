package com.cgg.service.baseconfig.controller.v1;

import com.cgg.framework.dto.response.None;
import com.cgg.framework.exception.BizFailException;
import com.cgg.service.baseconfig.api.ConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("configs")
@Tag(name = "配置服务")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @GetMapping("refresh")
    public Mono<None> refresh() {
        return configService.refreshConfig()
                .filter(BooleanUtils::isTrue)
                .switchIfEmpty(Mono.error(new BizFailException("配置刷新失败")))
                .thenReturn(None.INST);
    }

}
