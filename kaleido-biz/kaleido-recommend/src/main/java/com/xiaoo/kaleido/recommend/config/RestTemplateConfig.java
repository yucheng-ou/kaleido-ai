package com.xiaoo.kaleido.recommend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * RestTemplate配置类
 * <p>
 * 配置HTTP客户端，用于调用外部服务
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        
        // 设置连接超时时间
        factory.setConnectTimeout(Duration.ofSeconds(10));
        
        // 设置读取超时时间
        factory.setReadTimeout(Duration.ofSeconds(30));
        
        return new RestTemplate(factory);
    }
}
