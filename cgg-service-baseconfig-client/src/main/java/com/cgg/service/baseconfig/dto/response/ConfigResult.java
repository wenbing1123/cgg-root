package com.cgg.service.baseconfig.dto.response;

import com.cgg.framework.dto.response.ResponseData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class ConfigResult extends ResponseData {

    private String name;
    private String value;
    private String remark;

}
