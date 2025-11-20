package com.xiaoo.kaleido.user.infrastructure.adapter.repository;

import com.xiaoo.kaleido.user.domain.model.aggregate.UserOperateAggregate;
import com.xiaoo.kaleido.user.domain.model.entity.User;

/**
 * @author ouyucheng
 * @date 2025/11/18
 * @description 用户仓储服务接口 仓储服务与service解耦 让service只关注于核心业务实现
 */
public interface IUserOperateRepository {

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户id
     * @return 用户实体对象，如果不存在则返回null
     */
    User getById(Long id);

    /**
     * 根据邀请码查询用户信息
     *
     * @param inviteCode 邀请码
     * @return 用户信息
     */
    User getByInviteCode(String inviteCode);

    /**
     * 根据手机号查询用户信息
     *
     * @param telephone 用户手机号
     * @return 用户信息
     */
    User getByTelephone(String telephone);

    /**
     * 更新用户信息
     *
     * @param user 用户实体对象
     */
    void updateUser(User user);

    /**
     * 更新用户操作聚合根（用户实体 + 操作流水）
     *
     * @param userOperateAggregate 用户操作聚合根
     */
    void updateUserOperateAggregate(UserOperateAggregate userOperateAggregate);

    void saveUserOperateAggregate(UserOperateAggregate userOperateAggregate);
}
