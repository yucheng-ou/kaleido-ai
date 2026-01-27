package com.xiaoo.kaleido.coin;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.coin")
@EnableDubbo
public class KaleidoCoinApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoCoinApplication.class, args);
    }
}
