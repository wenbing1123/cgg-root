package com.cgg.service.user.dao.repository;

import com.cgg.service.user.dao.entity.User;
import com.cgg.service.user.dao.entity.UserLoginLog;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserLoginLogRepository extends R2dbcRepository<UserLoginLog, Long> {
}
