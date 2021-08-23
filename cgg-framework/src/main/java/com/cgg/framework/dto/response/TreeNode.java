package com.cgg.framework.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class TreeNode implements Serializable {

    private Long id;
    private Long pid;
    private String text;
    @Singular("ext")
    private Map<String, String> ext;

}
