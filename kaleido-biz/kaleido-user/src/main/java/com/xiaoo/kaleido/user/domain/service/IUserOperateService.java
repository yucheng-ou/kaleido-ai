package com.xiaoo.kaleido.user.domain.service;

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

    User getById(Long userId);
}
