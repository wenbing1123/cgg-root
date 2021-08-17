package com.cgg.app.bookkeeping.controller.v1.parammodel;

import com.cgg.framework.dto.request.PageQuery;
import com.cgg.framework.validate.Validate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "客户列表参数")
public class CustomerListPM extends PageQuery implements Validate {

    @Override
    public void validate() {
    }
}
