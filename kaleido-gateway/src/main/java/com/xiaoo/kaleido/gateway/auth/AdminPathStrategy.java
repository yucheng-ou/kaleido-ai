package com.xiaoo.kaleido.gateway.auth;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaoo.kaleido.satoken.util.StpAdminUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 管理员路径认证策略
 * <p>
 * 用于管理员相关路径的认证检查，需要管理员登录状态
 * 例如：管理后台、管理员相关API接口等
 * </p>
 */
@Slf4j
public class AdminPathStrategy implements LoginCheckStrategy {
    
    /**
     * 检查认证状态
     * <p>
     * 使用 Sa-Token 检查管理员登录状态
     * 如果管理员未登录，将抛出相应的异常
     * </p>
     */
    @Override
    public void checkAuth() {
        log.info("admin path check auth");
        StpAdminUtil.stpLogic.checkLogin();
    }
}
