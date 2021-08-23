package com.cgg.service.member.dao.repository;

import com.cgg.service.member.dao.entity.Member;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MemberRepository extends R2dbcRepository<Member, Long> {
}
