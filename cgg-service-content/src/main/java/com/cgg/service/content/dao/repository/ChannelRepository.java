package com.cgg.service.content.dao.repository;


import com.cgg.service.content.dao.entity.Channel;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ChannelRepository extends R2dbcRepository<Channel, Long> {
}
