package com.cgg.framework.oss;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadResult {

    private String etag;
    private String url;

}
