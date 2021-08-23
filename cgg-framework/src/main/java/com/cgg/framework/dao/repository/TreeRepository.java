package com.cgg.framework.dao.repository;

import com.cgg.framework.dao.entity.TreeEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TreeRepository<T extends TreeEntity> extends R2dbcRepository<T, Long> {
}
