package com.cgg.service.market.dao.repository;

import com.cgg.service.market.dao.entity.Activity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ActivityRepository extends R2dbcRepository<Activity, Long> {
}
