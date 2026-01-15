package com.xiaoo.kaleido.file.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO配置类
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(MinIOProperties.class)
public class MinIOConfig {

    /**
     * 创建MinioClient Bean
     *
     * @param properties MinIO配置属性
     * @return MinioClient实例
     */
    @Bean
    public MinioClient minioClient(MinIOProperties properties) {
        try {
            log.info("初始化MinioClient，endpoint: {}", properties.getEndpoint());
            return MinioClient.builder()
                    .endpoint(properties.getEndpoint())
                    .credentials(properties.getAccessKey(), properties.getSecretKey())
                    .build();
        } catch (Exception e) {
            log.error("MinioClient初始化失败: {}", e.getMessage(), e);
            throw new RuntimeException("MinioClient初始化失败", e);
        }
    }
}
