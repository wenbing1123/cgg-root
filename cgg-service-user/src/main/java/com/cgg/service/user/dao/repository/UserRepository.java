package com.cgg.service.user.dao.repository;

import com.cgg.service.user.dao.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {
}
