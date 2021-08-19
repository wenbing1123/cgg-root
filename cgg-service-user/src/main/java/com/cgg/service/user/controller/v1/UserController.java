package com.cgg.service.user.controller.v1;

import com.cgg.service.user.controller.v1.viewmodel.ProfileVM;
import com.cgg.service.user.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/user")
@Tag(name = "用户服务")
public class UserController {

    @GetMapping("profile")
    @Operation(tags = "个人信息")
    public Mono<ProfileVM> profile() {
        return SecurityUtils.getUser().map(x -> ProfileVM
                .builder()
                .username(x.getUsername())
                .build());
    }

}
