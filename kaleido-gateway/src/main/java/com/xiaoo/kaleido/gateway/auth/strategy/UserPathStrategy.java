package com.xiaoo.kaleido.gateway.auth.strategy;

import cn.dev33.satoken.router.SaRouter;
import com.xiaoo.kaleido.api.user.enums.UserStatus;
import com.xiaoo.kaleido.gateway.enums.UserPermissionEnum;
import com.xiaoo.kaleido.satoken.util.StpUserUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户路径认证策略
 * <p>
 * 用于用户相关路径的认证检查，需要用户登录状态
 * 例如：用户个人中心、用户相关API接口等
 */
@Slf4j
public class UserPathStrategy implements LoginCheckStrategy {

    /**
     * 检查认证状态

     * 使用 Sa-Token 检查用户登录状态
     * 如果用户未登录，将抛出相应的异常
     */
    @Override
    public void checkAuth() {
        log.info("校验普通用户是否登录...");
        StpUserUtil.stpLogic.checkLogin();

        //针对请求路径鉴权
        //TODO 后续细化处理
        SaRouter.match("/kaleido-user/**", r -> StpUserUtil.getStpLogic().checkPermissionOr(UserStatus.ACTIVE.name(), UserPermissionEnum.FROZEN.name()));
    }
}
