package com.xiaoo.kaleido.tag;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户服务启动类
 * <p>
 * Kaleido标签服务微服务入口
 *
 * @author ouyucheng
 * @date 2025/1/15
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.tag")
@EnableDubbo
public class KaleidoTagApplication {

    /**
     * 应用主入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(KaleidoTagApplication.class, args);
    }
}
