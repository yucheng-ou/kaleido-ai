package com.xiaoo.kaleido.lock.annotation;

import com.xiaoo.kaleido.lock.annotation.LockType;

import java.lang.annotation.*;

/**
 * 分布式锁注解
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {
    /**
     * 锁的键，支持 SpEL 表达式
     * 示例：'lock:order:' + #orderId
     */
    String key();
    
    /**
     * 锁的等待时间（秒），默认 0 表示不等待
     */
    long waitTime() default 0;
    
    /**
     * 锁的持有时间（秒），默认 30 秒
     */
    long leaseTime() default 30;
    
    /**
     * 锁类型：可重入锁、公平锁、读写锁等
     */
    LockType lockType() default LockType.REENTRANT;
    
    /**
     * 获取锁失败时的提示信息
     */
    String message() default "系统繁忙，请稍后再试";
}
