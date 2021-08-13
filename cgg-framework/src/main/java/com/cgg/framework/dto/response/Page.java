package com.cgg.framework.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Collection;

@Schema(name = "分页查询结果")
public class Page<T> extends ResponseData implements Serializable {

    @Schema(description = "记录总数")
    private final Long totalCount;
    @Schema(description = "数据列表")
    private final Collection<T> data;

    public Page(Long totalCount, Collection<T> data) {
        this.totalCount = totalCount;
        this.data = data;
    }

    public <E> Page<E> of(Long totalCount, Collection<E> content) {
        return new Page<>(totalCount, content);
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Collection<T> getData() {
        return data;
    }
}
