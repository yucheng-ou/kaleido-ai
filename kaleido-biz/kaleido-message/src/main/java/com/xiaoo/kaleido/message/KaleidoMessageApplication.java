package com.xiaoo.kaleido.message;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author ouyucheng
 * @date 2025/2/7
 * @description
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.message")
@EnableDubbo
public class KaleidoMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoMessageApplication.class, args);
    }

}
