package com.xiaoo.kaleido.api.user;

import com.github.pagehelper.PageInfo;
import com.xiaoo.kaleido.api.user.command.RegisterUserCommand;
import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
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

    /**
     * 冻结用户
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    Result<Void> freezeUser(@NotBlank String userId);

    /**
     * 解冻用户
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    Result<Void> unfreezeUser(@NotBlank String userId);

    /**
     * 删除用户（软删除）
     *
     * @param userId 用户ID
     * @return 操作结果
     */
    Result<Void> deleteUser(@NotBlank String userId);

    /**
     * 分页查询用户列表
     *
     * @param req 分页查询请求
     * @return 分页用户列表
     */
    Result<PageInfo<UserInfoResponse>> pageQueryUsers(@Valid UserPageQueryReq req);
}
