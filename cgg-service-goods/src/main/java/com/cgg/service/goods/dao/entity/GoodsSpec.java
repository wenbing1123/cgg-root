package com.cgg.service.goods.dao.entity;

import com.cgg.framework.dao.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("t_goods_spec")
public class GoodsSpec extends BaseEntity {

    private Long goodsId; //商品id
    private String sku; // 规格编号
    private String attrId; // 属性编号
    private String attrValueId; // 属性值编号
    private BigDecimal price; // 价格
    private String stock; // 库存

}
