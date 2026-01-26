package com.xiaoo.kaleido.recommend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.recommend")
@EnableDubbo
public class KaleidoRecommendApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoRecommendApplication.class, args);
    }
}
