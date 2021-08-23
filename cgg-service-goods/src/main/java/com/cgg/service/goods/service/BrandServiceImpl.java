package com.cgg.service.goods.service;

import com.cgg.framework.dto.response.Page;
import com.cgg.framework.service.AbstractBaseService;
import com.cgg.service.goods.api.BrandService;
import com.cgg.service.goods.dao.entity.Brand;
import com.cgg.service.goods.dto.command.BrandPageQuery;
import com.cgg.service.goods.dto.response.BrandResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("brandService")
@Slf4j
public class BrandServiceImpl extends AbstractBaseService<Brand> implements BrandService {

    @Override
    public Mono<Page<BrandResult>> getPage(BrandPageQuery qry) {
        return getPage(Criteria.empty(), qry.getOffset(), qry.getPageSize(), brand -> brand.covt(BrandResult.class));
    }

}
