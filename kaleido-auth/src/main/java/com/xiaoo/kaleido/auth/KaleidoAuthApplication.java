package com.xiaoo.kaleido.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ouyucheng
 * @date 2025/11/6
 * @description 授权服务启动入口
 */

@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.auth")
public class KaleidoAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoAuthApplication.class, args);
    }
}
