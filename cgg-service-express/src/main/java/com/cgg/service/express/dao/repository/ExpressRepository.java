package com.cgg.service.express.dao.repository;

import com.cgg.service.express.dao.entity.Express;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ExpressRepository extends R2dbcRepository<Express, Long> {
}
