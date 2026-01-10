package com.xiaoo.kaleido.api.user;

import com.xiaoo.kaleido.api.user.command.RegisterUserCommand;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * 用户RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/17
 * @dubbo
 */
public interface IRpcUserService {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    Result<UserInfoResponse> getById(@NotBlank String userId);

    /**
     * 根据手机号获取用户信息
     *
     * @param telephone 手机号
     * @return 用户信息
     */
    Result<UserInfoResponse> getByTelephone(@NotBlank String telephone);

    /**
     * 用户注册
     *
     * @param command 用户注册命令
     * @return 用户ID
     */
    Result<String> register(@Valid RegisterUserCommand command);

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
