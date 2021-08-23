package com.cgg.framework.dto.response;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class TreeNode implements Serializable {

    private Long id;
    private Long pid;
    private String text;
    private List<TreeNode> children;
    @Singular("ext")
    private Map<String, String> ext;

    public void addChild(TreeNode child) {
        if (this.children == null) {
            this.children = Lists.newArrayList();
        }

        this.children.add(child);
    }

}
