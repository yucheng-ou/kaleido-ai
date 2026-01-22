package com.xiaoo.kaleido.gateway.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.util.SaResult;
import com.xiaoo.kaleido.gateway.auth.strategy.DynamicStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * Sa-Token 网关全局过滤器配置类
 * <p>
 * 配置网关的全局认证过滤器，使用策略模式根据请求路径选择不同的认证策略
 * 支持从配置文件动态加载策略配置
 */
@Configuration
@Slf4j
public class SaTokenConfig {

    private final DynamicStrategyFactory dynamicStrategyFactory;

    /**
     * 构造函数，注入动态策略工厂
     */
    public SaTokenConfig(DynamicStrategyFactory dynamicStrategyFactory) {
        this.dynamicStrategyFactory = dynamicStrategyFactory;
    }

    /**
     * 创建 Sa-Token 全局过滤器 Bean

     * 配置网关的全局认证过滤器，包含以下功能：
     * 1. 排除公共路径（无需认证）
     * 2. 包含所有其他路径进行认证检查
     * 3. 使用动态策略工厂根据路径选择认证策略
     * 4. 全局异常处理
     *
     * @return 配置好的 SaReactorFilter 实例
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {

        return new SaReactorFilter()
                // 排除公共路径，无需认证（这些路径已经在策略工厂中配置为 PUBLIC 策略）
                // 注意：现在路径排除由策略工厂的配置决定，这里保持向后兼容
                .addExclude("/kaleido-admin/public/**")
                // 指定拦截路由：所有路径
                .addInclude("/**")
                // 设置认证逻辑：根据请求路径选择对应的认证策略
                .setAuth(obj -> {
                    String path = SaHolder.getRequest().getRequestPath();
                    dynamicStrategyFactory.getStrategy(path).checkAuth();
                })
                .setError(this::getSaResult);

    }

    /**
     * 处理认证异常，返回统一的错误响应
     *
     * @param throwable 捕获的异常
     * @return 统一的错误响应结果
     */
    private SaResult getSaResult(Throwable throwable) {
        if (Objects.requireNonNull(throwable) instanceof NotLoginException) {
            log.error("请先登录");
            return SaResult.error("请先登录");
        }
        log.error("sa-token认证异常：", throwable);
        return SaResult.error(throwable.getMessage());
    }
}
