package com.xiaoo.kaleido.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MinIO配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinIOProperties {

    /**
     * MinIO服务地址
     */
    private String endpoint;

    /**
     * 文件地址host
     */
    private String fileHost;

    /**
     * 存储桶bucket名称
     */
    private String bucketName;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;
}
