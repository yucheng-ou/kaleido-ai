package com.xiaoo.kaleido.api.user;

import com.xiaoo.kaleido.api.user.command.AddUserCommand;
import com.xiaoo.kaleido.api.user.response.UserInfoResponse;
import com.xiaoo.kaleido.base.result.Result;

/**
 * 用户RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public interface IRpcUserService {

    Result<UserInfoResponse> getById(String userId);

    Result<String> register(AddUserCommand command);

    /**
     * 记录用户登录
     *
     * @param userId 用户ID
     */
    Result<Void> login(String userId);

    /**
     * 记录用户登出
     *
     * @param userId 用户ID
     */
    Result<Void> logout(String userId);
}
