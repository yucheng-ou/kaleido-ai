package com.xiaoo.kaleido.aop.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP 自动配置类
 * <p>
 * 启用 AspectJ 自动代理，确保切面生效
 * </p>
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@AutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopAutoConfiguration {
}