package com.xiaoo.kaleido.api.admin.user;

import com.xiaoo.kaleido.api.admin.user.command.RegisterAdminCommand;
import com.xiaoo.kaleido.api.admin.user.response.AdminInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * 管理后台RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/19
 * @dubbo
 */
public interface IRpcAdminAuthService {


    /**
     * 管理员注册
     *
     * @param command 管理员注册命令
     * @return 管理员用户ID
     */
    Result<String> register(@Valid RegisterAdminCommand command);

    /**
     * 记录管理员登录
     *
     * @param adminId 管理员ID
     */
    Result<Void> login(@NotBlank String adminId);

    /**
     * 根据手机号查找管理员用户
     *
     * @param telephone 手机号
     * @return 管理员用户信息
     */
    Result<AdminInfoResponse> findByMobile(@NotBlank String telephone);

}
