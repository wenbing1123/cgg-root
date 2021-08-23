package com.cgg.service.goods.api;

import com.cgg.framework.dto.response.Page;
import com.cgg.service.goods.dto.command.BrandQuery;
import com.cgg.service.goods.dto.response.BrandResult;
import reactor.core.publisher.Mono;

public interface BrandService {

    Mono<Page<BrandResult>> getPage(BrandQuery qry);

}
