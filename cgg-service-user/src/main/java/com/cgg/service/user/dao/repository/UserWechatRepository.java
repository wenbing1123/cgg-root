package com.cgg.service.user.dao.repository;

import com.cgg.service.user.dao.entity.UserWechat;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserWechatRepository extends R2dbcRepository<UserWechat, Long> {

}
