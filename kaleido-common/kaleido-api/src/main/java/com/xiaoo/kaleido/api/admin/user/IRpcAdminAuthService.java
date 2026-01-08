package com.xiaoo.kaleido.api.admin.user;

import com.xiaoo.kaleido.api.admin.user.command.AddAdminCommand;
import com.xiaoo.kaleido.api.admin.user.response.AdminUserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * 管理后台RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
public interface IRpcAdminAuthService {


    Result<String> register(@Valid AddAdminCommand command);

    Result<Void> login(@NotBlank String adminUserId);

    Result<AdminUserInfoResponse> findByMobile(@NotBlank String telephone);

}
