package com.xiaoo.kaleido.user.domain.service;

import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.user.domain.model.valobj.InvitationCode;

/**
 * 用户领域服务接口
 * <p>
 * 处理跨实体的用户业务逻辑，包括用户创建、状态管理、权限分配等核心领域操作
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface IUserDomainService {

    /**
     * 创建用户

     * 根据手机号和邀请码创建新用户，系统会自动生成用户ID并设置初始状态
     *
     * @param telephone  手机号，必须符合手机号格式规范
     * @param inviteCode 邀请人邀请码，可为空表示无邀请人
     * @return 用户聚合根，包含完整的用户信息
     */
    UserAggregate createUser(String telephone, String inviteCode);

    /**
     * 根据ID查找用户，如果不存在则抛出异常

     * 用于命令操作中需要确保用户存在的场景
     *
     * @param userId 用户ID，不能为空
     * @return 用户聚合根，包含完整的用户信息
     */
    UserAggregate findByIdOrThrow(String userId);

    /**
     * 修改用户昵称

     * 更新用户的昵称信息，会记录操作日志
     *
     * @param userId   用户ID，不能为空
     * @param nickName 新昵称，长度限制为2-20个字符
     * @return 更新后的用户聚合根
     */
    UserAggregate changeNickName(String userId, String nickName);

    /**
     * 冻结用户

     * 将用户状态设置为冻结，冻结后用户无法登录和操作
     *
     * @param userId 用户ID，不能为空
     * @return 冻结后的用户聚合根
     */
    UserAggregate freezeUser(String userId);

    /**
     * 解冻用户

     * 将用户状态从冻结恢复为正常
     *
     * @param userId 用户ID，不能为空
     * @return 解冻后的用户聚合根
     */
    UserAggregate unfreezeUser(String userId);

    /**
     * 删除用户（软删除）

     * 将用户标记为删除状态，实际数据保留
     *
     * @param userId 用户ID，不能为空
     * @return 删除后的用户聚合根
     */
    UserAggregate deleteUser(String userId);

    /**
     * 更新用户头像

     * 更新用户的头像URL
     *
     * @param userId    用户ID，不能为空
     * @param avatarUrl 头像URL，必须是有效的URL格式
     * @return 更新后的用户聚合根
     */
    UserAggregate updateAvatar(String userId, String avatarUrl);

    /**
     * 用户登录

     * 记录用户登录操作，更新最后登录时间
     *
     * @param userId 用户ID，不能为空
     * @return 更新后的用户聚合根
     */
    UserAggregate login(String userId);

    /**
     * 用户登出

     * 记录用户登出操作
     *
     * @param userId 用户ID，不能为空
     * @return 用户聚合根
     */
    UserAggregate logout(String userId);

    /**
     * 生成邀请码

     * 为用户生成唯一的邀请码，用于邀请新用户注册
     *
     * @return 邀请码值对象，包含邀请码字符串和生成时间
     */
    InvitationCode generateInvitationCode();

}
