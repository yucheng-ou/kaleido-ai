package com.xiaoo.kaleido.ai;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ai服务启动类
 *
 * @author ouyucheng
 * @date 2025/1/27
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.ai")
@EnableDubbo
public class KaleidoAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaleidoAiApplication.class, args);
    }
}
