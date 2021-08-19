package com.cgg.service.user.dto.response;

import com.cgg.framework.dto.response.ResponseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserResult extends ResponseData {

    private Long id;
    private String username;
    private String password;
    private Integer status;

}
