package com.cgg.app.bookkeeping.controller.v1.parammodel;

import com.cgg.framework.dto.request.Command;
import com.cgg.framework.validate.Validate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "客户创建参数")
public class CustomerCreatePM extends Command implements Validate {
    @Override
    public void validate() {

    }
}
