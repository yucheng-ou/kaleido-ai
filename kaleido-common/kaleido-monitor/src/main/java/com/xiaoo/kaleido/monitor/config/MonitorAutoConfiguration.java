package com.xiaoo.kaleido.monitor.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 监控自动配置类
 */
@AutoConfiguration
public class MonitorAutoConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 自定义指标注册器，为所有指标添加应用名称标签
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
        return registry -> registry.config().commonTags("application", applicationName);
    }
}
