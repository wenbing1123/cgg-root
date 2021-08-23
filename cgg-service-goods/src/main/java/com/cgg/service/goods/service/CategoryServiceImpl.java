package com.cgg.service.goods.service;

import com.cgg.framework.dto.response.Tree;
import com.cgg.framework.dto.response.TreeNode;
import com.cgg.service.goods.api.CategoryService;
import com.cgg.service.goods.dao.entity.Category;
import com.cgg.service.goods.dao.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Service("categoryService")
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    @Override
    public Mono<Tree> getTree() {
        return categoryRepository
                .findAll(Example.of(Category
                        .builder()
                        .build()))
                .map(x -> TreeNode
                        .builder()
                        .id(x.getId())
                        .pid(x.getPid())
                        .text(x.getName())
                        .ext("", "")
                        .build())
                .collectList()
                .map(Tree::of);
    }
}
