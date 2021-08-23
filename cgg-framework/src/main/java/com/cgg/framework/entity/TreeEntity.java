package com.cgg.framework.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TreeEntity extends BaseEntity {

    private Long lft;
    private Long rgt;
    private Long pid;
    private String name;
    private Integer level; //层级，从1开始

}
