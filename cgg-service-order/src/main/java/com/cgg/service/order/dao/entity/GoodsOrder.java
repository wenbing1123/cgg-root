package com.cgg.service.order.dao.entity;

import com.cgg.service.order.dao.entity.base.BaseOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("t_goods_order")
public class GoodsOrder extends BaseOrder {
}
