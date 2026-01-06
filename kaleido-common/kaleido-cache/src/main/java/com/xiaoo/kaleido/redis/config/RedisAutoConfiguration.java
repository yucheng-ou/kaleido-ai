package com.xiaoo.kaleido.redis.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.xiaoo.kaleido.redis.service.RedissonService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author ouyucheng
 * @date 2025/11/20
 * @description
 */
@AutoConfiguration
@EnableMethodCache(basePackages = "com.xiaoo.kaleido")
@Import({RedissonService.class})
public class RedisAutoConfiguration {

}
