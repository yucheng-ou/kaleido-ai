package com.xiaoo.langchain4j.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Langchain4j 演示应用启动类
 *
 * @author ouyucheng
 * @date 2026-02-26
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.langchain4j.demo")
public class Langchain4jDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Langchain4jDemoApplication.class, args);
    }
}
