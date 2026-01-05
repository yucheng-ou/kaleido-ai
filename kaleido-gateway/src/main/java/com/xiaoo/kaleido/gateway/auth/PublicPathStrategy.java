package com.xiaoo.kaleido.gateway.auth;

/**
 * 公共路径认证策略
 * <p>
 * 用于公共路径的认证检查，此类路径无需任何登录认证
 * 例如：登录页面、公开API接口等
 * </p>
 */
public class PublicPathStrategy implements LoginCheckStrategy {
    
    /**
     * 检查认证状态
     * <p>
     * 公共路径无需认证，因此此方法为空实现
     * </p>
     */
    @Override
    public void checkAuth() {
        // 公共路径无需认证检查
    }
}
