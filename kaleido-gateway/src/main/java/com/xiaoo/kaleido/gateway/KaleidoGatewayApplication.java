package com.xiaoo.kaleido.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ouyucheng
 * @date 2025/11/5
 * @description 网关服务启动入口
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.gateway")
public class KaleidoGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoGatewayApplication.class, args);
    }
}
