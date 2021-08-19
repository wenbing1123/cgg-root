package com.cgg.service.user.dto.command;

import com.cgg.framework.dto.request.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserSaveCommand extends Command {

    private Long userId; //用户编号
    private String username; // 用户名
    private String phone; // 手机号
    private String email; //邮箱
    private String password;
    private String nickName; //昵称
    private String avatarUrl; // 头像
    private Integer status; //用户状态

}
