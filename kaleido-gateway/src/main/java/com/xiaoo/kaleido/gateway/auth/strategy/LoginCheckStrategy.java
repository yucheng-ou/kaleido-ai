package com.xiaoo.kaleido.gateway.auth.strategy;

/**
 * 登录检查策略接口
 * <p>
 * 策略模式中的策略接口，定义不同路径的认证检查行为
 */
public interface LoginCheckStrategy {

    /**
     * 检查认证状态
     * <p>
     * 根据具体策略实现检查用户或管理员的登录状态
     */
    void checkAuth();
}
