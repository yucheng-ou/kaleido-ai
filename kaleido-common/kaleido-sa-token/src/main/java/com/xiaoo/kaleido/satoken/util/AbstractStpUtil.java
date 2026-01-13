package com.xiaoo.kaleido.satoken.util;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;

import java.util.List;

/**
 * 抽象的Stp工具类，使用泛型支持不同类型的登录
 * 这个类提供静态工具方法，通过传入类型参数来区分不同的登录类型
 */
public class AbstractStpUtil {
    
    /**
     * 获取当前登录用户的ID
     *
     * @param stpLogic StpLogic实例
     * @param notLoginError 未登录错误码
     * @return 当前登录用户的ID
     * @throws BizException 如果没有用户登录，将抛出此异常
     */
    public static <T extends ErrorCode> String getLoginId(StpLogic stpLogic, T notLoginError) {
        Object loginId = stpLogic.getLoginId();
        if (loginId == null) {
            throw BizException.of(notLoginError);
        }
        return String.valueOf(loginId);
    }
    
    /**
     * 用户登录
     *
     * @param stpLogic StpLogic实例
     * @param userId 用户id
     * @return 令牌信息
     */
    public static SaTokenInfo login(StpLogic stpLogic, String userId) {
        stpLogic.login(userId);
        return getTokenInfo(stpLogic);
    }
    
    /**
     * 获取当前请求的令牌信息
     *
     * @param stpLogic StpLogic实例
     * @return 当前请求的令牌信息
     */
    public static SaTokenInfo getTokenInfo(StpLogic stpLogic) {
        return stpLogic.getTokenInfo();
    }
    
    /**
     * 退出登录
     *
     * @param stpLogic StpLogic实例
     */
    public static void logout(StpLogic stpLogic) {
        stpLogic.logout();
    }
    
    /**
     * 获取当前登录用户的权限列表
     *
     * @param stpLogic StpLogic实例
     * @return 当前登录用户的权限列表
     */
    public static List<String> getPermissionList(StpLogic stpLogic) {
        return stpLogic.getPermissionList();
    }
    
    /**
     * 获取当前登录用户的角色列表
     *
     * @param stpLogic StpLogic实例
     * @return 当前登录用户的角色列表
     */
    public static List<String> getRoleList(StpLogic stpLogic) {
        return stpLogic.getRoleList();
    }
    
    /**
     * 判断当前用户是否登录
     *
     * @param stpLogic StpLogic实例
     * @return 是否登录
     */
    public static Boolean isLogin(StpLogic stpLogic) {
        return stpLogic.isLogin();
    }
}
