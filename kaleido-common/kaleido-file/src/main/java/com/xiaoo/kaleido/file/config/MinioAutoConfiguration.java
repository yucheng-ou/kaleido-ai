package com.xiaoo.kaleido.file.config;

import com.xiaoo.kaleido.file.service.IMinIOService;
import com.xiaoo.kaleido.file.service.impl.MinIOServiceImpl;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * MinIO自动配置类
 */
@AutoConfiguration
@Import({MinIOConfig.class,})
public class MinioAutoConfiguration {

    /**
     * 创建MinIOService Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public IMinIOService minIOService(MinioClient minioClient, MinIOProperties properties) {
        return new MinIOServiceImpl(minioClient, properties);
    }
}
