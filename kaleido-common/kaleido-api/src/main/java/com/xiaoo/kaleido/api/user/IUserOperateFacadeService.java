package com.xiaoo.kaleido.api.user;

import com.xiaoo.kaleido.api.user.request.UpdateUserInfoRequest;
import com.xiaoo.kaleido.api.user.request.UserRegisterRequest;
import com.xiaoo.kaleido.api.user.response.UserOperateVo;
import com.xiaoo.kaleido.base.result.Result;

/**
 * 用户操作门面服务接口
 * 
 * @author ouyucheng
 * @date 2025/11/19
 */
public interface IUserOperateFacadeService {

    /**
     * 用户注册
     * 
     * @param request 用户注册请求参数，包含手机号、验证码等信息
     * @return 用户操作结果
     */
    Result<UserOperateVo> register(UserRegisterRequest request);

    /**
     * 根据用户ID查询用户信息
     * 
     * @param userId 用户ID
     * @return 用户操作结果
     */
    Result<UserOperateVo> getById(Long userId);

    /**
     * 更新用户基本信息
     * 
     * @param request 更新用户信息请求参数
     * @return 用户操作结果
     */
    Result<UserOperateVo> updateUserInfo(UpdateUserInfoRequest request);
}
