package com.xiaoo.kaleido.distribute.worker;

import com.xiaoo.kaleido.redis.service.RedissonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

/**
 * @author ouyucheng
 * @date 2025/12/15
 * @description 机器idHolder
 */
@RequiredArgsConstructor
public class WorkerIdHolder implements CommandLineRunner {

    private final RedissonService redissonService;

    @Value("${spring.application.name}")
    private String applicationName;

    //机器id
    public static long WORKER_ID;


    //启动时执行生成机器id
    //根据启动顺序生成 数据保存在redis
    //保证相同服务上不同机器之间不重复
    @Override
    public void run(String... args) {
        WORKER_ID = redissonService.incr(applicationName) % 32;
    }
}
