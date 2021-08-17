package com.cgg.service.market.dao.repository;

import com.cgg.service.market.dao.entity.UserRedPacket;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRedPacketRepository extends R2dbcRepository<UserRedPacket, Long> {
}
