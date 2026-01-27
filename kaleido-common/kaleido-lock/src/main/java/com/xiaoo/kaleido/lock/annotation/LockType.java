package com.xiaoo.kaleido.lock.annotation;

/**
 * 锁类型枚举
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
public enum LockType {
    /**
     * 可重入锁
     */
    REENTRANT,
    
    /**
     * 公平锁
     */
    FAIR,
    
    /**
     * 读锁
     */
    READ,
    
    /**
     * 写锁
     */
    WRITE,
    
    /**
     * 联锁
     */
    MULTI
}
