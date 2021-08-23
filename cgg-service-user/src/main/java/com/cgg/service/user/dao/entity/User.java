package com.cgg.service.user.dao.entity;

import com.cgg.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("t_user")
public class User extends BaseEntity {

    private String username; // 用户名
    private String phone; // 手机号
    private String email; //邮箱
    private String password;
    @Column("nick_name")
    private String nickName; //昵称
    @Column("avatar_url")
    private String avatarUrl; // 头像
    private Integer status; //用户状态

}
