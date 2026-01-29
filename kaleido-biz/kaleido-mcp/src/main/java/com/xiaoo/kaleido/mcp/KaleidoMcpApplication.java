package com.xiaoo.kaleido.mcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * mcp服务启动类
 *
 * @author ouyucheng
 * @date 2025/1/29
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.mcp")
public class KaleidoMcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaleidoMcpApplication.class, args);
    }
}
