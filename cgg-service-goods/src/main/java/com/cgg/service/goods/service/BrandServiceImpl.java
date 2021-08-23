package com.cgg.service.goods.service;

import com.cgg.framework.dto.response.Page;
import com.cgg.service.goods.api.BrandService;
import com.cgg.service.goods.dao.repository.BrandRepository;
import com.cgg.service.goods.dto.command.BrandQuery;
import com.cgg.service.goods.dto.response.BrandResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Service("brandService")
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandRepository brandRepository;

    @Override
    public Mono<Page<BrandResult>> getPage(BrandQuery qry) {
        return brandRepository
                .count()
                .flatMap(c -> brandRepository
                        .findAll()
                        .map(x -> BrandResult
                                .builder()
                                .build())
                        .collectList()
                        .map(l -> Page.of(c, l)));
    }

}
