package com.xiaoo.kaleido.redis.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.xiaoo.kaleido.redis.service.DelayDeleteService;
import com.xiaoo.kaleido.redis.service.RedissonService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Redis 自动配置
 *
 * @author ouyucheng
 * @date 2025/11/20
 */
@AutoConfiguration
@EnableMethodCache(basePackages = "com.xiaoo.kaleido")
@Import({RedissonService.class, DelayDeleteService.class})
public class RedisAutoConfiguration {

}
