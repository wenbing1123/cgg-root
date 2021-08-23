package com.cgg.framework.dto.request;

import com.cgg.framework.constants.CggConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(name = "树查询参数")
public abstract class TreeQuery extends Query {

    @Schema(description = "节点编号")
    private Long id;
    @Schema(description = "子孙节点深度")
    private Integer depth;

    public TreeQuery(Long id, Integer depth) {
        this.id = id == null || id <= 0 ? CggConstants.TREE_ROOT_ID : id;
        this.depth = depth == null || depth <= 0 ? Integer.MAX_VALUE : depth;
    }

}
