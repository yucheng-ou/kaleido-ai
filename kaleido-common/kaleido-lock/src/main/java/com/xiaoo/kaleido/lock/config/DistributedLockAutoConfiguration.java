package com.xiaoo.kaleido.lock.config;

import com.xiaoo.kaleido.lock.aspect.DistributedLockAspect;
import com.xiaoo.kaleido.lock.lock.DistributedLockService;
import com.xiaoo.kaleido.lock.lock.LockSpelExpressionParser;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 分布式锁自动配置
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
@AutoConfiguration
@Import({DistributedLockService.class, DistributedLockAspect.class, LockSpelExpressionParser.class})
public class DistributedLockAutoConfiguration {
    
}
