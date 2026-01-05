package com.xiaoo.kaleido.gateway.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.util.SaResult;
import com.xiaoo.kaleido.gateway.auth.DynamicStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 网关全局过滤器配置类
 * <p>
 * 配置网关的全局认证过滤器，使用策略模式根据请求路径选择不同的认证策略
 * 支持从配置文件动态加载策略配置
 * </p>
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
     * <p>
     * 配置网关的全局认证过滤器，包含以下功能：
     * 1. 排除公共路径（无需认证）
     * 2. 包含所有其他路径进行认证检查
     * 3. 使用动态策略工厂根据路径选择认证策略
     * 4. 全局异常处理
     * 5. 跨域请求支持
     * </p>
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
                // 设置全局异常处理
                .setError(e -> {
                    log.error("sa-token全局异常:", e);
                    return SaResult.error(e.getMessage());
                })
                // 设置认证前的处理（主要用于跨域配置）
                .setBeforeAuth(obj -> {
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");
                    // 处理 OPTIONS 预检请求
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> {
                            })
                            .back();
                });
    }
}
