package com.cgg.service.baseconfig.dao.repository;

import com.cgg.service.baseconfig.dao.entity.Config;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ConfigRepository extends R2dbcRepository<Config, Long> {

    @Query("select t.* from t_config t where t.name = :name")
    Mono<Config> findByName(String name);

}
