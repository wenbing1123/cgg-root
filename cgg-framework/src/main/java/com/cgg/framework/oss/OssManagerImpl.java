package com.cgg.framework.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.InputStream;

@Slf4j
public class OssManagerImpl implements OssManager, InitializingBean, DisposableBean {

    private OSSClient client;

    private String ossAddress;
    private String accessKeyId;
    private String accessKeySecret;

    public OssManagerImpl() {
    }

    public OssManagerImpl(String ossAddress, String accessKeyId, String accessKeySecret) {
        this.ossAddress = ossAddress;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }

    public OssManagerImpl(OSSClient client) {
        this.client = client;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.client == null) {
            this.client = new OSSClient(this.ossAddress,
                    new DefaultCredentialProvider(this.accessKeyId, this.accessKeySecret),
                    new ClientConfiguration());
        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.client != null) {
            this.client.shutdown();
        }
    }

    @Override
    public Mono<UploadResult> upload(String bucketName, String key, InputStream payload) {
        return Mono.defer(() -> {
            PutObjectResult result = this.client.putObject(bucketName, key, payload);
            return Mono.just(UploadResult
                    .builder()
                    .etag(result.getETag())
                    .url("")
                    .build());
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
