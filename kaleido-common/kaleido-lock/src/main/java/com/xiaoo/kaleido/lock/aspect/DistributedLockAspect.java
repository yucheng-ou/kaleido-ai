package com.xiaoo.kaleido.lock.aspect;

import com.xiaoo.kaleido.lock.annotation.DistributedLock;
import com.xiaoo.kaleido.lock.annotation.LockType;
import com.xiaoo.kaleido.lock.lock.DistributedLockService;
import com.xiaoo.kaleido.lock.lock.LockSpelExpressionParser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分布式锁切面
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
@Aspect
@Slf4j
@Component
public class DistributedLockAspect {

    @Resource
    private DistributedLockService distributedLockService;
    
    @Resource
    private LockSpelExpressionParser spelExpressionParser;
    
    /**
     * 分布式锁切面处理
     *
     * @param joinPoint 连接点
     * @param distributedLock 分布式锁注解
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // 解析 SpEL 表达式生成锁键
        String keyExpression = distributedLock.key();
        String dynamicKey = spelExpressionParser.parse(keyExpression, method, joinPoint.getArgs());
        
        // 构建完整的锁键
        String fullKey = "kaleido:lock:" + dynamicKey;
        
        // 获取锁参数
        LockType lockType = distributedLock.lockType();
        long waitTime = distributedLock.waitTime();
        long leaseTime = distributedLock.leaseTime();
        String message = distributedLock.message();
        
        log.info("尝试获取分布式锁，锁键: {}, 锁类型: {}, 等待时间: {}秒, 持有时间: {}秒",
                 fullKey, lockType, waitTime, leaseTime);
        
        try {
            // 尝试获取锁
            distributedLockService.tryLockOrThrow(fullKey, lockType, waitTime, leaseTime, message);
            
            log.info("成功获取分布式锁，锁键: {}", fullKey);
            
            // 执行原方法
            return joinPoint.proceed();
            
        } finally {
            // 释放锁
            try {
                distributedLockService.unlock(fullKey, lockType);
                log.info("释放分布式锁，锁键: {}", fullKey);
            } catch (Exception e) {
                log.error("释放分布式锁失败，锁键: {}", fullKey, e);
            }
        }
    }
}
