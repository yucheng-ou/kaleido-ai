package com.xiaoo.kaleido.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ouyucheng
 * @date 2025/11/18
 * @description
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.user")
@EnableDubbo
public class KaleidoUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoUserApplication.class, args);
    }
}
