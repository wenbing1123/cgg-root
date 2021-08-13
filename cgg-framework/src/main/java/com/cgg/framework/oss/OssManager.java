package com.cgg.framework.oss;

import reactor.core.publisher.Mono;

import java.io.InputStream;

public interface OssManager {

    Mono<UploadResult> upload(String bucketName, String key, InputStream payload);

}
