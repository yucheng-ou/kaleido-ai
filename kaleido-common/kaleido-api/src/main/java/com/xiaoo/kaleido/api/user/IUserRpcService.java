package com.xiaoo.kaleido.api.user;

import com.xiaoo.kaleido.api.user.request.CreateUserRpcRequest;
import com.xiaoo.kaleido.api.user.response.UserResponse;
import com.xiaoo.kaleido.base.result.Result;

import jakarta.validation.Valid;

/**
 * 用户RPC服务接口
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public interface IUserRpcService {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    Result<UserResponse> getUserById(String userId);

    /**
     * 根据手机号获取用户信息
     *
     * @param telephone 手机号
     * @return 用户信息
     */
    Result<UserResponse> getUserByTelephone(String telephone);

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 用户ID
     */
    Result<String> createUser(@Valid CreateUserRpcRequest request);


    /**
     * 冻结用户
     *
     * @param userId 用户ID
     * @param reason 冻结原因
     * @return 冻结结果
     */
    Result<Boolean> freezeUser(String userId, String reason);

    /**
     * 解冻用户
     *
     * @param userId 用户ID
     * @param reason 解冻原因
     * @return 解冻结果
     */
    Result<Boolean> unfreezeUser(String userId, String reason);

    /**
     * 验证用户是否存在
     *
     * @param userId 用户ID
     * @return 是否存在
     */
    Result<Boolean> existsUser(String userId);

    /**
     * 验证手机号是否已注册
     *
     * @param telephone 手机号
     * @return 是否已注册
     */
    Result<Boolean> existsTelephone(String telephone);

    /**
     * 记录用户登录
     * - 更新用户最后登录时间
     * - 记录登录日志
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    Result<Boolean> login(String userId);

    /**
     * 记录用户登出
     * - 记录登出日志
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    Result<Boolean> logout(String userId);
}
