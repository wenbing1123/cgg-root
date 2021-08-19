package com.cgg.service.account.dto.response;

import com.cgg.framework.dto.response.ResponseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AccountResult extends ResponseData {

    private Long accountId;

}
