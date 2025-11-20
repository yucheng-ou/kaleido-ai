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

    /**
     * 查询用户列表（不分页）
     * 根据查询条件返回匹配的用户列表
     *
     * @param request 用户查询请求参数
     * @return 用户列表
     */
    java.util.List<User> listUsers(com.xiaoo.kaleido.api.user.request.UserQueryRequest request);

    /**
     * 分页查询用户列表
     * 根据查询条件和分页参数返回分页结果
     *
     * @param request 用户查询请求参数
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 用户列表
     */
    java.util.List<User> listUsers(com.xiaoo.kaleido.api.user.request.UserQueryRequest request, int page, int size);
}
