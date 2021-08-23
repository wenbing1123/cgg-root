package com.cgg.service.baseconfig.dao.entity;

import com.cgg.framework.dao.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
@Table("t_config")
public class Config extends BaseEntity {

    @NonNull
    private String name;
    @NonNull
    private String value;
    private String remark; // 备注

}
