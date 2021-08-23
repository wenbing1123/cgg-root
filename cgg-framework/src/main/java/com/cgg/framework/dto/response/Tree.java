package com.cgg.framework.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Collection;

@Schema(name = "树查询结果")
public class Tree extends ResponseData implements Serializable {

    @Schema(description = "节点总数")
    private final Collection<TreeNode> nodes;

    public Tree(Collection<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public static Tree of(Collection<TreeNode> nodes) {
        return new Tree(nodes);
    }

    public Collection<TreeNode> getNodes() {
        return nodes;
    }
}
