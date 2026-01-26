package com.xiaoo.kaleido.limiter.config;

import com.xiaoo.kaleido.limiter.aspect.RateLimitAspect;
import com.xiaoo.kaleido.limiter.limit.RateLimitService;
import com.xiaoo.kaleido.limiter.limit.RateLimitSpelExpressionParser;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 限流自动配置
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@AutoConfiguration
@Import({RateLimitService.class, RateLimitAspect.class, RateLimitSpelExpressionParser.class})
public class RateLimitAutoConfiguration {
    
}
