package com.cgg.service.user.dto.command;

import com.cgg.framework.dto.request.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserWechatSaveCommand extends Command {

    private Long userId; // 关联系统用户
    private String openId; // 开发者编号
    private String nickName;
    private String gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;

}
