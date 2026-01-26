package com.xiaoo.kaleido.redis.service;

import com.alicp.jetcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 延迟删除服务
 *
 * @author ouyucheng
 */
public class DelayDeleteService implements DisposableBean {
    
    private static final Logger log = LoggerFactory.getLogger(DelayDeleteService.class);

    private final ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(5);

    /**
     * 延迟删除缓存（默认2秒延迟）
     *
     * @param cache 缓存实例
     * @param key   缓存键
     */
    public void delayedCacheDelete(Cache cache, String key) {
        delayedCacheDelete(cache, key, 2, TimeUnit.SECONDS);
    }

    /**
     * 延迟删除缓存（自定义延迟时间）
     *
     * @param cache    缓存实例
     * @param key      缓存键
     * @param delay    延迟时间
     * @param timeUnit 时间单位
     */
    public void delayedCacheDelete(Cache cache, String key, long delay, TimeUnit timeUnit) {
        log.debug("调度延迟删除缓存, key: {}, 延迟: {} {}", key, delay, timeUnit);

        scheduler.schedule(() -> {
            try {
                cache.remove(key);
            } catch (Exception e) {
                log.error("删除缓存失败,", e);
            }
        }, delay, timeUnit);
    }

    /**
     * 关闭线程池，释放资源
     * 在Spring容器关闭时自动调用
     */
    @Override
    public void destroy() {
        if (!scheduler.isShutdown()) {
            log.info("关闭延迟删除服务线程池");
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
