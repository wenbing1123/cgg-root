package com.cgg.framework.config.properties;

public class OssProperties {

    private String ossAddress;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String ossImageUrl;

    public String getOssAddress() {
        return ossAddress;
    }

    public void setOssAddress(String ossAddress) {
        this.ossAddress = ossAddress;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getOssImageUrl() {
        return ossImageUrl;
    }

    public void setOssImageUrl(String ossImageUrl) {
        this.ossImageUrl = ossImageUrl;
    }
}
