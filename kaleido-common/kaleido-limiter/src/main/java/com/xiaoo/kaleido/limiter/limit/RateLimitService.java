package com.xiaoo.kaleido.limiter.limit;

import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import jakarta.annotation.Resource;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 限流服务
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Service
public class RateLimitService {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 尝试获取令牌（集群模式）
     *
     * @param key    限流键
     * @param limit  限流数量
     * @param window 窗口大小（秒）
     * @return 是否获取成功
     */
    public boolean tryAcquire(String key, int limit, int window) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 设置速率：limit 个请求 / window 秒
        rateLimiter.trySetRate(RateType.OVERALL, limit, Duration.ofSeconds(window));
        return rateLimiter.tryAcquire();
    }

    /**
     * 尝试获取令牌（带自定义消息）
     *
     * @param key     限流键
     * @param limit   限流数量
     * @param window  窗口大小（秒）
     * @param message 限流提示信息
     */
    public void tryAcquireOrThrow(String key, int limit, int window, String message) {
        if (!tryAcquire(key, limit, window)) {
            throw new BizException(BizErrorCode.SYSTEM_BUSY.getCode(), message);
        }
    }

    /**
     * 尝试获取令牌（根据限流类型）
     *
     * @param key     限流键
     * @param limit   限流数量
     * @param window  窗口大小（秒）
     * @param message 限流提示信息
     */
    public void tryAcquire(String key, int limit, int window, String message) {
        tryAcquireOrThrow(key, limit, window, message);
    }
}
