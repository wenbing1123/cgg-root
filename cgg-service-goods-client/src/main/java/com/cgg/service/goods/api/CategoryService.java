package com.cgg.service.goods.api;

import com.cgg.framework.dto.response.Tree;
import com.cgg.service.goods.dto.command.CategoryTreeQuery;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Mono<Tree> getTree(CategoryTreeQuery qry);

}
