package com.cgg.service.content.dao.repository;


import com.cgg.service.content.dao.entity.Ad;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface AdRepository extends R2dbcRepository<Ad, Long> {
}
