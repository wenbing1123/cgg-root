package com.cgg.service.goods.dto.command;

import com.cgg.framework.dto.request.TreeQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Schema(name = "分类查询参数")
public class CategoryTreeQuery extends TreeQuery {
}
