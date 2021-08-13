package com.cgg.service.content.dao.repository;


import com.cgg.service.content.dao.entity.Banner;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface BannerRepository extends R2dbcRepository<Banner, Long> {
}
