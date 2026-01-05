package com.xiaoo.kaleido.gateway.auth.config;

import cn.hutool.core.util.StrUtil;
import com.xiaoo.kaleido.gateway.enums.AuthStrategyEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 网关认证策略配置属性类
 * <p>
 * 用于从配置文件加载认证策略配置，支持动态配置管理
 * </p>
 */
@Data
@Component
@Slf4j
@ConfigurationProperties(prefix = "gateway.auth")
public class AuthStrategyConfig {

    /**
     * 认证策略配置列表
     * <p>
     * 列表顺序即为匹配优先级，先配置的优先级更高
     * </p>
     */
    private List<StrategyItem> strategies = new ArrayList<>();

    /**
     * 默认策略类型
     * <p>
     * 当没有路径匹配时使用的默认策略
     * 可选值：PUBLIC, USER, ADMIN
     * 默认值：PUBLIC
     * </p>
     */
    private AuthStrategyEnum defaultStrategy = AuthStrategyEnum.PUBLIC;

    /**
     * 单个策略配置项
     */
    @Data
    public static class StrategyItem {

        /**
         * 路径模式
         * <p>
         * 支持 Ant 风格路径匹配，例如：/kaleido-auth/**
         * </p>
         */
        private String pattern;

        /**
         * 策略类型
         * <p>
         * 可选值：
         * - PUBLIC: 公共路径，无需认证
         * - USER: 用户路径，需要用户登录
         * - ADMIN: 管理员路径，需要管理员登录
         * </p>
         */
        private AuthStrategyEnum strategy;
    }

    /**
     * 获取默认策略配置（用于未匹配路径）
     *
     * @return 默认策略配置项
     */
    public StrategyItem getDefaultStrategyItem() {
        StrategyItem defaultItem = new StrategyItem();
        defaultItem.setPattern("**");
        defaultItem.setStrategy(defaultStrategy);
        return defaultItem;
    }

    /**
     * 验证配置是否有效
     *
     * @throws IllegalArgumentException 如果配置无效
     */
    public void validate() {
        if (strategies == null) {
            strategies = new ArrayList<>();
        }

        // 验证策略配置 只打错误日志 不抛异常
        for (StrategyItem item : strategies) {
            if (StrUtil.isBlank(item.getPattern())) {
                log.error("认证策略路径不能为空");
            }
            if (item.getStrategy() == null) {
                log.error("认证策略不能为空");
            }
        }

        // 默认策略已经通过枚举类型保证有效性，无需额外验证
        if (defaultStrategy == null) {
            defaultStrategy = AuthStrategyEnum.PUBLIC;
        }
    }
}
