package com.cgg.service.content.dao.repository;


import com.cgg.service.content.dao.entity.Topic;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TopicRepository extends R2dbcRepository<Topic, Long> {
}
