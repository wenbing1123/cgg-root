package com.cgg.service.goods.api;

import com.cgg.framework.dto.response.Tree;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Mono<Tree> getTree();

}
