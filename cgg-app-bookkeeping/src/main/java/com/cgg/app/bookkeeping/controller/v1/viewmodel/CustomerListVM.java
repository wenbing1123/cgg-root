package com.cgg.app.bookkeeping.controller.v1.viewmodel;

import com.cgg.framework.dto.response.ResponseData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(name="客户列表结果")
public class CustomerListVM extends ResponseData {
}
