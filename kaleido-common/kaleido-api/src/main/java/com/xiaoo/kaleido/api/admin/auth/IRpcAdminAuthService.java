package com.xiaoo.kaleido.api.admin.auth;

import com.xiaoo.kaleido.api.admin.auth.command.AddAdminUserCommand;
import com.xiaoo.kaleido.api.admin.auth.command.AdminLoginCommand;
import com.xiaoo.kaleido.api.admin.auth.response.AdminLoginResponse;
import com.xiaoo.kaleido.api.admin.auth.response.AdminUserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;

/**
 * 管理后台RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
public interface IRpcAdminAuthService {


    Result<String> register(AddAdminUserCommand command);

    Result<Void> login(String adminUserId);

    Result<AdminUserInfoResponse> findByMobile(String telephone);

}
