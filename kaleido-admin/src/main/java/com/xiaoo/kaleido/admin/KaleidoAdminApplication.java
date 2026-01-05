package com.xiaoo.kaleido.admin;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ouyucheng
 * @date 2025/12/25
 * @description
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.admin")
@EnableDubbo
public class KaleidoAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoAdminApplication.class, args);
    }
}
