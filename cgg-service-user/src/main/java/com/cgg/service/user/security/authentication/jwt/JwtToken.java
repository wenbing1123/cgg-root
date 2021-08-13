package com.cgg.service.user.security.authentication.jwt;

import com.cgg.framework.dto.response.ResponseData;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor(staticName = "of")
public class JwtToken extends ResponseData {

    @NonNull
    private String token;

}
