package com.xiaoo.kaleido.wardrobe;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author ouyucheng
 * @date 2025/12/25
 * @description
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.wardrobe")
@EnableDubbo
public class KaleidoWardrobeApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoWardrobeApplication.class, args);
    }
}
