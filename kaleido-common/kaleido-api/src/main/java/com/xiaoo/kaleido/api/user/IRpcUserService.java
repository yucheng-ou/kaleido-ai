package com.xiaoo.kaleido.api.user;

import com.xiaoo.kaleido.api.user.command.AddUserCommand;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * 用户RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public interface IRpcUserService {

    Result<UserInfoResponse> getById(@NotBlank String userId);

    Result<UserInfoResponse> getByTelephone(@NotBlank String telephone);

    Result<String> register(@Valid AddUserCommand command);

    /**
     * 记录用户登录
     *
     * @param userId 用户ID
     */
    Result<Void> login(@NotBlank String userId);

    /**
     * 记录用户登出
     *
     * @param userId 用户ID
     */
    Result<Void> logout(@NotBlank String userId);
}
