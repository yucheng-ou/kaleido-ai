package com.xiaoo.kaleido.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户服务启动类
 * 
 * Kaleido用户微服务入口，提供用户相关的领域服务，包括用户管理、认证、权限等功能
 * 
 * @author ouyucheng
 * @date 2025/11/18
 */
@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.user")
@EnableDubbo
public class KaleidoUserApplication {
    
    /**
     * 应用主入口
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(KaleidoUserApplication.class, args);
    }
}
