package com.cgg.service.content.dao.repository;


import com.cgg.service.content.dao.entity.Content;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ContentRepository extends R2dbcRepository<Content, Long> {
}
