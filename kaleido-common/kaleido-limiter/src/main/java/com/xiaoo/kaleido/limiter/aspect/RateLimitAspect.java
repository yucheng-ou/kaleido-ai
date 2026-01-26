package com.xiaoo.kaleido.limiter.aspect;

import com.xiaoo.kaleido.limiter.annotation.RateLimit;
import com.xiaoo.kaleido.limiter.limit.RateLimitService;
import com.xiaoo.kaleido.limiter.limit.RateLimitSpelExpressionParser;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 限流切面
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Aspect
@Component
public class RateLimitAspect {
    
    private static final Logger log = LoggerFactory.getLogger(RateLimitAspect.class);
    
    @Resource
    private RateLimitService rateLimitService;
    
    @Resource
    private RateLimitSpelExpressionParser spelExpressionParser;
    
    /**
     * 限流切面处理
     *
     * @param joinPoint 连接点
     * @param rateLimit 限流注解
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // 解析 SpEL 表达式生成限流键
        String keyExpression = rateLimit.key();
        String dynamicKey = spelExpressionParser.parse(keyExpression, method, joinPoint.getArgs());
        
        // 构建完整的限流键
        String fullKey = "kaleido:rate:limit:" + dynamicKey;
        
        log.debug("执行限流检查，限流键: {}, 限制: {}/{}秒", fullKey, rateLimit.limit(), rateLimit.window());
        
        // 执行限流检查
        rateLimitService.tryAcquireOrThrow(
            fullKey, 
            rateLimit.limit(), 
            rateLimit.window(), 
            rateLimit.message()
        );
        
        // 执行原方法
        return joinPoint.proceed();
    }
}
