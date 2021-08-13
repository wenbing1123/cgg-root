package com.cgg.service.user.config;

import com.cgg.service.user.security.authentication.*;
import com.cgg.service.user.security.authentication.jwt.JwtAuthenticationProvider;
import com.cgg.service.user.security.authentication.phone.PhoneAuthorizationProvider;
import com.cgg.service.user.security.authentication.phone.PhoneLoginAuthenticationWebFilter;
import com.cgg.service.user.security.authentication.username.UsernameAuthenticationProvider;
import com.cgg.service.user.security.authentication.username.UsernameLoginAuthenticationWebFilter;
import com.cgg.service.user.security.authentication.wechat.WechatAuthenticationProvider;
import com.cgg.service.user.security.authentication.wechat.WechatLoginAuthenticationWebFilter;
import com.cgg.service.user.security.authorization.JsonServerAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * web安全配置
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .cors(corsSpec -> {
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("*");
                    config.setAllowCredentials(true);
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
                    source.registerCorsConfiguration("/**", config);
                    corsSpec.configurationSource(source);
                })
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(serverAuthenticationEntryPoint())
                .and().securityContextRepository(redisServerSecurityContextRepository())
                .addFilterBefore(wechatLoginAuthenticationWebFilter(), SecurityWebFiltersOrder.FORM_LOGIN)
                .addFilterBefore(phoneLoginAuthenticationWebFilter(), SecurityWebFiltersOrder.FORM_LOGIN)
                .addFilterBefore(usernameLoginAuthenticationWebFilter(), SecurityWebFiltersOrder.FORM_LOGIN)
                .authorizeExchange()
                .pathMatchers(AUTH_WHITELIST).permitAll()
                .anyExchange().authenticated()
                .and().build();
    }

    @Bean
    RedisServerSecurityContextRepository redisServerSecurityContextRepository() {
        return new RedisServerSecurityContextRepository();
    }

    @Bean
    MultiAuthenticationManager authenticationManager() {
        return new MultiAuthenticationManager();
    }

    @Bean
    AuthenticationWebFilter wechatLoginAuthenticationWebFilter() {
        AuthenticationWebFilter filter = new WechatLoginAuthenticationWebFilter(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(failureHandler());
        return filter;
    }

    @Bean
    WechatAuthenticationProvider wechatAuthenticationProvider() { return new WechatAuthenticationProvider(); }

    @Bean
    AuthenticationWebFilter phoneLoginAuthenticationWebFilter() {
        AuthenticationWebFilter filter = new PhoneLoginAuthenticationWebFilter(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(failureHandler());
        return filter;
    }

    @Bean
    PhoneAuthorizationProvider phoneAuthorizationProvider() { return new PhoneAuthorizationProvider(); }

    @Bean
    AuthenticationWebFilter usernameLoginAuthenticationWebFilter() {
        AuthenticationWebFilter filter = new UsernameLoginAuthenticationWebFilter(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(failureHandler());
        return filter;
    }

    @Bean
    UsernameAuthenticationProvider usernameAuthenticationProvider() { return new UsernameAuthenticationProvider(); }

    @Bean
    ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
        return new JsonServerAuthenticationEntryPoint();
    }

    @Bean
    ServerAuthenticationSuccessHandler successHandler() {
        return new JsonServerAuthenticationSuccessHandler();
    }

    @Bean
    ServerAuthenticationFailureHandler failureHandler() {
        return new JsonServerAuthenticationFailureHandler();
    }

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ServerAccessDeniedHandler accessDeniedHandler() {
        return new JsonServerAccessDeniedHandler();
    }


    private static final String[] AUTH_WHITELIST = new String[] {
            "/login/sms_verify_code",
            "/login/wechat_code",
            "/login/captcha_code",
            "/actuator/**",
            "/v3/api-docs/**",
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/security",
            "/swagger-ui.html",
            "/css/**",
            "/js/**",
            "/images/**",
            "/webjars/**",
            "/favicon.ico"
    };
}
