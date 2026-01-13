package com.xiaoo.kaleido.auth;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 授权服务启动入口
 * 
 * Kaleido系统的授权认证服务，负责用户和管理员的登录、注册、权限验证等功能
 * 
 * @author ouyucheng
 * @date 2025/11/6
 */

@SpringBootApplication(scanBasePackages = "com.xiaoo.kaleido.auth")
@EnableDubbo
public class KaleidoAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaleidoAuthApplication.class, args);
    }
}
