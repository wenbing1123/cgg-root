package com.cgg.framework.dto.request;

import com.cgg.framework.constants.CggConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(name = "分页查询参数")
public class PageQuery extends Query {

    @Schema(description = "页码")
    private Integer pageNo;
    @Schema(description = "每页大小")
    private Integer pageSize;

    public PageQuery(int pageNo, int pageSize) {
        this.pageNo = Math.max(pageNo, 1);
        this.pageSize = Math.max(pageSize, CggConstants.DEFAULT_PAGE_SIZE);
    }

    @Schema(hidden = true)
    public Integer getOffset() {
        return (this.pageNo - 1) * pageSize;
    }

}
