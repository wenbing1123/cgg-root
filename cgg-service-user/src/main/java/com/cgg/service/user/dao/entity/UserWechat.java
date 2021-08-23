package com.cgg.service.user.dao.entity;

import com.cgg.framework.dao.entity.BaseEntity;
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
@Table("t_user_wechat")
public class UserWechat extends BaseEntity {

    @Column("user_id")
    private Long userId; // 关联系统用户
    @Column("open_id")
    private String openId; // 开发者编号
    @Column("nick_name")
    private String nickName;
    private String gender;
    private String language;
    private String city;
    private String province;
    private String country;
    @Column("avatar_url")
    private String avatarUrl;
    @Column("union_id")
    private String unionId;

}
