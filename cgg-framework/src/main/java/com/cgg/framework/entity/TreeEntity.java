package com.cgg.framework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TreeEntity extends BaseEntity {

    private String name;
    private Long pid;
    private Long lft;
    private Long rgt;
    private Integer level;

}
