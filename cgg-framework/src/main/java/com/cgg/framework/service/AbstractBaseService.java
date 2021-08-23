package com.cgg.framework.service;

import com.cgg.framework.dto.response.Page;
import com.cgg.framework.entity.Entity;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

public abstract class AbstractBaseService<T extends Entity> {

    @Resource
    private R2dbcEntityTemplate r2dbcEntityTemplate;
    protected Class<T> entityClass;

    protected AbstractBaseService() {
        Type superClass = this.getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) ((ParameterizedType)superClass).getActualTypeArguments()[0];
    }

    protected R2dbcEntityTemplate template() {
        return r2dbcEntityTemplate;
    }

    protected <R> Mono<Page<R>> getPage(Criteria criteria, int offset, int limit, Function<T,R> f) {
        return r2dbcEntityTemplate
                .select(entityClass)
                .matching(Query.query(criteria))
                .count()
                .flatMap(c -> r2dbcEntityTemplate
                        .select(entityClass)
                        .matching(Query.query(criteria).offset(offset).limit(limit))
                        .all()
                        .map(f)
                        .collectList()
                        .map(l -> Page.of(c, l)));

    }

}
