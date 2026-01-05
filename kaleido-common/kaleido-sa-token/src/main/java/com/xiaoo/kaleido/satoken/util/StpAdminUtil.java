package com.xiaoo.kaleido.satoken.util;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpLogic;
import com.xiaoo.kaleido.base.exception.BizErrorCode;
import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.satoken.enums.LoginTypeEnum;

import java.util.List;

public class StpAdminUtil {
    public static StpLogic stpLogic = new StpLogic(LoginTypeEnum.ADMIN.name());


    /**
     * 获取当前登录用户的ID
     * 此方法用于从stpLogic对象中获取登录ID如果登录ID为空，则抛出异常，
     * 表示当前没有用户登录这是为了确保方法的调用方能够明确地知道登录状态，
     * 并进行相应的处理
     *
     * @return 当前登录用户的ID，如果未登录，则抛出异常
     * @throws BizException 如果没有用户登录，将抛出此异常，提示"没有登录"
     */
    public static Integer getLoginId() {
        Object loginId = stpLogic.getLoginId();
        if (loginId == null) {
            throw  BizException.of(BizErrorCode.ADMIN_NOT_LOGIN);
        }
       return Integer.parseInt(loginId.toString());
    }

    /**
     * 管理员登录
     * @param adminId 管理员id
     * @return 管理员信息
     */
    public static SaTokenInfo login(String adminId) {
        stpLogic.login(adminId);
        return getTokenInfo();
    }


    /**
     * 获取当前请求的令牌信息
     * 此方法用于从stpLogic对象中提取当前请求的令牌信息SaTokenInfo对象包含了有关令牌的各种详细信息，
     * 如用户ID、令牌过期时间等此方法抽象了底层的令牌获取逻辑，为调用者提供了更高级别的封装，
     * 使其无需关注令牌存储和管理的细节
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
    public static List<String> getPermissionList() {
        return stpLogic.getPermissionList();
    }

    /**
     * 获取当前登录用户的角色列表
     * @return 当前登录用户的角色列表，如果当前用户没有登录，将返回null
     */
    public static List<String> getRoleList() {
        return stpLogic.getRoleList();
    }

    /**
     * 获取当前登录用户的权限列表（兼容旧版本）
     * @return 当前登录用户的权限列表，如果当前用户没有登录，将返回null
     * @deprecated 使用 {@link #getPermissionList()} 代替
     */
    @Deprecated
    public static List<String> permissions() {
        return getPermissionList();
    }

    /**
     * 判断当前用户是否登录
     * @return 是否登录
     */
    public static Boolean isLogin() {
        return stpLogic.isLogin();
    }
}
