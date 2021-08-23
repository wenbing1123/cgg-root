package com.cgg.service.goods.dto.command;

import com.cgg.framework.dto.request.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Schema(name = "品牌查询参数")
public class BrandQuery extends PageQuery {
}
