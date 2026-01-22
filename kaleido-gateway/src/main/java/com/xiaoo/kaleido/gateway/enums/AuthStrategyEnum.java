package com.xiaoo.kaleido.gateway.enums;

import lombok.Getter;

/**
 * 认证策略枚举
 * <p>
 * 定义网关支持的认证策略类型，用于动态策略工厂的配置和路由
 *
 * @author ouyucheng
 */
@Getter
public enum AuthStrategyEnum {
    /**
     * 公共路径策略

     * 无需任何认证，任何人都可以访问
     * 适用于登录页面、公开API接口等
     */
    PUBLIC,

    /**
     * 用户路径策略

     * 需要用户登录认证
     * 适用于用户个人中心、用户相关API接口等
     */
    USER,

    /**
     * 管理员路径策略

     * 需要管理员登录认证
     * 适用于管理后台、管理员相关API接口等
     */
    ADMIN;
}
