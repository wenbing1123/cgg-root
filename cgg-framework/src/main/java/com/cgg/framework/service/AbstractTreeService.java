package com.cgg.framework.service;

import com.cgg.framework.constants.CggConstants;
import com.cgg.framework.dto.response.Tree;
import com.cgg.framework.dto.response.TreeNode;
import com.cgg.framework.entity.TreeEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractTreeService<T extends TreeEntity> extends AbstractBaseService<T> {

    /**
     * 获取所有的子孙节点
     */
    public Mono<Tree> getChildren(Long id, Integer depth, Function<T, TreeNode> f) {
        if (CggConstants.TREE_ROOT_ID == id) {
            return retrieveTree(0, Integer.MAX_VALUE, depth, f);
        }
        return template()
                .selectOne(Query.query(Criteria.where("id").is(id)), entityClass)
                .flatMap(p -> retrieveTree(p.getLft(), p.getRgt(), p.getLevel() + depth, f));

    }

    public Mono<Tree> getChildren(Long id, Function<T, TreeNode> f) {
        return getChildren(id, Integer.MAX_VALUE, f);
    }

    private Mono<Tree> retrieveTree(long lft, long rgt, int level, Function<T, TreeNode> f) {
        return template()
                .select(entityClass)
                .matching(Query
                        .query(Criteria
                                .where("lft").between(lft, rgt)
                                .and("level").lessThanOrEquals(level))
                        .sort(Sort.by(Sort.Order.asc("lft"))))
                .all()
                .collectList()
                .map(list -> {
                    List<TreeNode> root = Lists.newArrayList();
                    Map<Long, TreeNode> tmpMap = Maps.newHashMap();

                    for (T t : list) {
                        TreeNode curr = f.apply(t);
                        curr.setId(t.getId());
                        curr.setPid(t.getPid());
                        curr.setText(t.getName());

                        TreeNode parent = tmpMap.get(curr.getPid());
                        if (parent != null) {
                            parent.addChild(curr);
                        }

                        tmpMap.put(curr.getId(), curr);
                    }

                    return Tree.of(root);
                });
    }

}
