package com.cgg.framework.dao.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntity extends Entity {

    @CreatedDate
    @Column("gmt_create")
    private LocalDateTime gmtCreate;

    @LastModifiedDate
    @Column("gmt_modified")
    private LocalDateTime gmtModified;

}
