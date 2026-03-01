package com.xiaoo.kaleido.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AI面试助手服务启动类
 *
 * @author ouyucheng
 * @date 2026/02/27
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.interview")
public class KaleidoInterviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaleidoInterviewApplication.class, args);
    }
}
