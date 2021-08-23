package com.cgg.service.goods.service;

import com.cgg.framework.dto.response.Tree;
import com.cgg.framework.dto.response.TreeNode;
import com.cgg.framework.service.AbstractTreeService;
import com.cgg.service.goods.api.CategoryService;
import com.cgg.service.goods.dao.entity.Category;
import com.cgg.service.goods.dto.command.CategoryTreeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service("categoryService")
@Slf4j
public class CategoryServiceImpl extends AbstractTreeService<Category> implements CategoryService {

    @Override
    public Mono<Tree> getTree(CategoryTreeQuery qry) {
        return getChildren(qry.getId(), qry.getDepth(), category -> TreeNode
                .builder()
                .ext("", "")
                .build());
    }

}
