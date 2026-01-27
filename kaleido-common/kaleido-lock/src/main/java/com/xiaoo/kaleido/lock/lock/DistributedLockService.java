package com.xiaoo.kaleido.lock.lock;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.lock.annotation.LockType;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁服务
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
@Service
public class DistributedLockService {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取锁对象
     *
     * @param key      锁键
     * @param lockType 锁类型
     * @return 锁对象
     */
    public RLock getLock(String key, LockType lockType) {
        return switch (lockType) {
            case REENTRANT -> redissonClient.getLock(key);
            case FAIR -> redissonClient.getFairLock(key);
            case READ -> {
                RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(key);
                yield readWriteLock.readLock();
            }
            case WRITE -> {
                RReadWriteLock writeLock = redissonClient.getReadWriteLock(key);
                yield writeLock.writeLock();
            }
            case MULTI ->
                // 联锁需要多个键，这里返回一个普通的可重入锁
                // 实际使用中需要根据具体需求处理
                    redissonClient.getLock(key);
        };
    }

    /**
     * 获取联锁对象（多个键）
     *
     * @param keys 锁键数组
     * @return 联锁对象
     */
    public RLock getMultiLock(String... keys) {
        RLock[] locks = new RLock[keys.length];
        for (int i = 0; i < keys.length; i++) {
            locks[i] = redissonClient.getLock(keys[i]);
        }
        return redissonClient.getMultiLock(locks);
    }

    /**
     * 尝试获取锁
     *
     * @param key       锁键
     * @param lockType  锁类型
     * @param waitTime  等待时间（秒）
     * @param leaseTime 持有时间（秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String key, LockType lockType, long waitTime, long leaseTime) {
        RLock lock = getLock(key, lockType);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 尝试获取锁，失败时抛出异常
     *
     * @param key       锁键
     * @param lockType  锁类型
     * @param waitTime  等待时间（秒）
     * @param leaseTime 持有时间（秒）
     * @param message   失败提示信息
     */
    public void tryLockOrThrow(String key, LockType lockType, long waitTime, long leaseTime, String message) {
        if (!tryLock(key, lockType, waitTime, leaseTime)) {
            throw new BizException(BizErrorCode.SYSTEM_BUSY.getCode(), message);
        }
    }

    /**
     * 获取锁（阻塞）
     *
     * @param key       锁键
     * @param lockType  锁类型
     * @param leaseTime 持有时间（秒）
     */
    public void lock(String key, LockType lockType, long leaseTime) {
        RLock lock = getLock(key, lockType);
        if (leaseTime > 0) {
            lock.lock(leaseTime, TimeUnit.SECONDS);
        } else {
            lock.lock();
        }
    }

    /**
     * 释放锁
     *
     * @param key      锁键
     * @param lockType 锁类型
     */
    public void unlock(String key, LockType lockType) {
        RLock lock = getLock(key, lockType);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    /**
     * 强制释放锁（不检查持有者）
     *
     * @param key      锁键
     * @param lockType 锁类型
     */
    public void forceUnlock(String key, LockType lockType) {
        RLock lock = getLock(key, lockType);
        if (lock.isLocked()) {
            lock.forceUnlock();
        }
    }

    /**
     * 检查锁是否被当前线程持有
     *
     * @param key      锁键
     * @param lockType 锁类型
     * @return 是否被当前线程持有
     */
    public boolean isHeldByCurrentThread(String key, LockType lockType) {
        RLock lock = getLock(key, lockType);
        return lock.isHeldByCurrentThread();
    }
}
