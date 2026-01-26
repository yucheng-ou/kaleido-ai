package com.xiaoo.kaleido.limiter.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {
    /**
     * 限流键，支持 SpEL 表达式
     * 示例：'rate:limit:user:' + #userId
     */
    String key();
    
    /**
     * 限流数量（单位时间内允许的请求数）
     */
    int limit();
    
    /**
     * 窗口大小（秒）
     */
    int window();
    
    /**
     * 限流时的提示信息
     */
    String message() default "请求过于频繁，请稍后再试";
}
