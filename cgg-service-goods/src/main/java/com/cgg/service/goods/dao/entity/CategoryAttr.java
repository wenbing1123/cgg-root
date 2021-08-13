package com.cgg.service.goods.dao.entity;

import com.cgg.framework.entity.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("t_category_attr")
public class CategoryAttr extends TreeEntity {

    private Long categoryId;       // 分类编号
    private String attrName;       // 属性名
    private String valueType;      // 取值类型（1：单选 2：多选   3：输入）
    private String necessaryFlag;  // 是否必须项（0：否 1：是  默认0）
    private Integer priority;      // 排序

}
