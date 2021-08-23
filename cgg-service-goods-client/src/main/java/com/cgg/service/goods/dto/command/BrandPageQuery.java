package com.cgg.service.goods.dto.command;

import com.cgg.framework.dto.request.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Schema(name = "品牌查询参数")
public class BrandPageQuery extends PageQuery {
}
