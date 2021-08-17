package com.cgg.service.market.dao.repository;

import com.cgg.service.market.dao.entity.RedPacket;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RedPacketRepository extends R2dbcRepository<RedPacket, Long> {
}
