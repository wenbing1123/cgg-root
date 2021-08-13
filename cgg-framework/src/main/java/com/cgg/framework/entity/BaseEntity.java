package com.cgg.framework.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {

    @Id
    private Long id;

    @CreatedDate
    @Column("gmt_create")
    private LocalDateTime gmtCreate;

    @LastModifiedDate
    @Column("gmt_modified")
    private LocalDateTime gmtModified;

}
