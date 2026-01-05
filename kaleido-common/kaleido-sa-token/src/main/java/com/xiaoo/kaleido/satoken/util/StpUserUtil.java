package com.xiaoo.kaleido.satoken.util;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.satoken.enums.LoginTypeEnum;

public class StpUserUtil {

    public static StpLogic stpLogic = new StpLogic(LoginTypeEnum.USER.name());

    /**
     * 获取当前登录用户的ID
     * 此方法用于从stpLogic对象中获取登录ID如果登录ID为空，则抛出异常，
     *
     * @return 当前登录用户的ID，即openId
     * @throws BizException 如果没有用户登录，将抛出此异常，提示"没有登录"
     */
    public static String getLoginId() {
        Object loginId = stpLogic.getLoginId();
        if (loginId == null) {
            throw  BizException.of(BizErrorCode.USER_NOT_LOGIN);
        }
        return loginId.toString();
    }

    /**
     * 用户登录
     * @param userId 用户id
     * @return 用户信息
     */
    public static SaTokenInfo login(String userId) {
        stpLogic.login(userId);
        return getTokenInfo();
    }


    /**
     * 获取当前请求的令牌信息
     *
     * @return 当前请求的令牌信息如果当前请求没有关联的令牌，或者令牌解析失败，将返回null
     */
    public static SaTokenInfo getTokenInfo() {
        return stpLogic.getTokenInfo();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        stpLogic.logout();
    }

    /**
     * 获取当前登录用户的权限列表
     * @return 当前登录用户的权限列表，如果当前用户没有登录，将返回null
     */
    public static java.util.List<String> getPermissionList() {
        return stpLogic.getPermissionList();
    }

    /**
     * 获取当前登录用户的角色列表
     * @return 当前登录用户的角色列表，如果当前用户没有登录，将返回null
     */
    public static java.util.List<String> getRoleList() {
        return stpLogic.getRoleList();
    }

    public static Boolean isLogin() {
        return stpLogic.isLogin();
    }
}
