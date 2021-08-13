package com.cgg.service.user.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@SuperBuilder
@AllArgsConstructor
public class SecurityUser {

    private String userId; // 用户编号
    private String username; // 用户名
    private Collection<? extends GrantedAuthority> authorities; // 权限列表

}
