package com.cgg.service.member.dao.repository;

import com.cgg.service.member.dao.entity.MemberGroup;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MemberGroupRepository extends R2dbcRepository<MemberGroup, Long> {
}
