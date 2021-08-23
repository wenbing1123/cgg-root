package com.cgg.service.store.dao.repository;

import com.cgg.service.store.dao.entity.Store;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface StoreRepository extends R2dbcRepository<Store, Long> {
}
