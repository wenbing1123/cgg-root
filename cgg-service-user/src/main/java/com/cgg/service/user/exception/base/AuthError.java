package com.cgg.service.user.exception.base;

import com.cgg.framework.dto.response.ResponseData;
import com.cgg.service.user.enums.AuthType;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
public class AuthError {

    @NonNull
    private AuthType authType;
    @NonNull
    private String principal;
    private ResData data;

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    public static class ResData extends ResponseData {

        private Integer errorCount;

    }
}
