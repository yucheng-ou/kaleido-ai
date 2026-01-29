package com.xiaoo.kaleido.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ai服务启动类
 *
 * @author ouyucheng
 * @date 2025/1/27
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.ai")
public class KaleidoAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaleidoAiApplication.class, args);
    }
}
