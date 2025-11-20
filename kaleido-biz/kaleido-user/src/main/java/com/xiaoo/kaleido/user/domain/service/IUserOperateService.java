package com.xiaoo.kaleido.user.domain.service;

import com.xiaoo.kaleido.api.user.request.UpdateUserInfoRequest;
import com.xiaoo.kaleido.user.domain.model.entity.User;

/**
 * @author ouyucheng
 * @date 2025/11/18
 * @description 用户操作领域 负责用户注册 信息修改 查询 状态变更等工作
 */
public interface IUserOperateService {


    /**
     * 注册用户
     *
     * @param telephone  用户手机号
     * @param inviteCode 邀请码
     * @return 用户信息
     */
    User register(String telephone, String inviteCode);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户操作结果
     */
    User getById(Long userId);

    /**
     * 更新用户基本信息
     * 包括昵称、头像和手机号的更新
     *
     * @param request 更新用户信息请求参数
     * @return 更新后的用户实体对象
     */
    User updateUserInfo(UpdateUserInfoRequest request);
}
