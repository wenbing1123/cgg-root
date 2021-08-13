package com.cgg.service.user.security.authentication.jwt;

import com.cgg.service.user.security.SecurityUser;

public interface JwtManager {

    JwtToken createToken(SecurityUser user);
    SecurityUser verifyToken(String token);

}
