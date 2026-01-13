package com.xiaoo.kaleido.satoken.util;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import lombok.Getter;

import java.util.List;

/**
 * 用户Stp工具类
 */
public class StpUserUtil {

    /**
     * 登录类型
     */
    public static final String TYPE = "USER";

    /**
     * StpLogic实例
     */
    @Getter
    public static final StpLogic stpLogic = new StpLogic(TYPE);

    /**
     * 获取当前登录用户的ID
     * 此方法用于从stpLogic对象中获取登录ID如果登录ID为空，则抛出异常，
     *
     * @return 当前登录用户的ID，即openId
     * @throws com.xiaoo.kaleido.base.exception.BizException 如果没有用户登录，将抛出此异常，提示"没有登录"
     */
    public static String getLoginId() {
        return AbstractStpUtil.getLoginId(stpLogic, BizErrorCode.USER_NOT_LOGIN);
    }

    /**
     * 用户登录
     *
     * @param userId 用户id
     * @return 用户信息
     */
    public static SaTokenInfo login(String userId) {
        return AbstractStpUtil.login(stpLogic, userId);
    }

    /**
     * 获取当前请求的令牌信息
     *
     * @return 当前请求的令牌信息如果当前请求没有关联的令牌，或者令牌解析失败，将返回null
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
     * 判断当前用户是否登录
     *
     * @return 是否登录
     */
    public static Boolean isLogin() {
        return AbstractStpUtil.isLogin(stpLogic);
    }

}
