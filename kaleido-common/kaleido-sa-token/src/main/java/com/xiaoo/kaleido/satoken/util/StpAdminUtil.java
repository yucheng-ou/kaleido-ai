package com.xiaoo.kaleido.satoken.util;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import lombok.Getter;

import java.util.List;

/**
 * 管理员Stp工具类
 */
public class StpAdminUtil {

    /**
     * 登录类型
     */
    public static final String TYPE = "ADMIN";

    /**
     * StpLogic实例
     */
    @Getter
    public static final StpLogic stpLogic = new StpLogic(TYPE);

    /**
     * 获取当前登录用户的ID
     *
     * @return 当前登录用户的ID
     * @throws com.xiaoo.kaleido.base.exception.BizException 如果没有用户登录，将抛出此异常
     */
    public static String getLoginId() {
        return AbstractStpUtil.getLoginId(stpLogic, BizErrorCode.ADMIN_NOT_LOGIN);
    }

    /**
     * 管理员登录
     *
     * @param adminId 管理员id
     * @return 管理员信息
     */
    public static SaTokenInfo login(String adminId) {
        return AbstractStpUtil.login(stpLogic, adminId);
    }

    /**
     * 获取当前请求的令牌信息
     *
     * @return 当前请求的令牌信息，如果当前请求没有关联的令牌，将返回null
     */
    public static SaTokenInfo getTokenInfo() {
        return AbstractStpUtil.getTokenInfo(stpLogic);
    }

    /**
     * 退出登录
     */
    public static void logout() {
        AbstractStpUtil.logout(stpLogic);
    }

    /**
     * 获取当前登录用户的权限列表
     *
     * @return 当前登录用户的权限列表，如果当前用户没有登录，将返回null
     */
    public static List<String> getPermissionList() {
        return AbstractStpUtil.getPermissionList(stpLogic);
    }

    /**
     * 获取当前登录用户的角色列表
     *
     * @return 当前登录用户的角色列表，如果当前用户没有登录，将返回null
     */
    public static List<String> getRoleList() {
        return AbstractStpUtil.getRoleList(stpLogic);
    }

    /**
     * 获取当前登录用户的权限列表（兼容旧版本）
     *
     * @return 当前登录用户的权限列表，如果当前用户没有登录，将返回null
     * @deprecated 使用 {@link #getPermissionList()} 代替
     */
    @Deprecated
    public static List<String> permissions() {
        return AbstractStpUtil.getPermissionList(stpLogic);
    }

    /**
     * 判断当前用户是否登录
     *
     * @return 是否登录
     */
    public static Boolean isLogin() {
        return AbstractStpUtil.isLogin(stpLogic);
    }

}
