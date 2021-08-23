package com.cgg.framework.dao.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TreeEntity extends BaseEntity {

    private String name;
    private Long pid;
    private Long lft;
    private Long rgt;
    private Integer level;

}
