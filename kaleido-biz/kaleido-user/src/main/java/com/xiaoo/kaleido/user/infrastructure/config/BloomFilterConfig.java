package com.xiaoo.kaleido.user.infrastructure.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 布隆过滤器配置类
 * 
 * 配置Redisson布隆过滤器，用于快速判断邀请码是否可能已存在
 * 
 * @author ouyucheng
 * @date 2026/1/13
 */
@Configuration
public class BloomFilterConfig {

    /**
     * 邀请码布隆过滤器
     * 
     * 用于快速判断邀请码是否可能已存在，减少数据库查询压力
     * 特性：
     * - 预期容量：100万
     * - 误判率：1%
     * - 内存占用：约1.7MB
     * 
     * @param redissonClient Redisson客户端
     * @return 邀请码布隆过滤器
     */
    @Bean
    public RBloomFilter<String> inviteCodeBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("user:inviteCode:bloom");
        
        // 初始化布隆过滤器
        // 预期容量：100万，误判率：1%
        // 注意：如果布隆过滤器已经存在，tryInit不会重新初始化
        bloomFilter.tryInit(1_000_000L, 0.01);
        
        return bloomFilter;
    }
}
