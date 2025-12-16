package com.xiaoo.kaleido.user.domain.service;

import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import com.xiaoo.kaleido.user.domain.model.entity.User;
import com.xiaoo.kaleido.user.domain.model.valobj.InvitationCode;

/**
 * 用户领域服务接口
 * 处理跨实体的用户业务逻辑
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface UserDomainService {

    /**
     * 创建用户
     *
     * @param telephone    手机号
     * @param passwordHash 密码哈希
     * @param inviterId    邀请人ID（可选）
     * @return 用户聚合根
     */
    UserAggregate createUser(String telephone, String passwordHash, String inviterId);

    /**
     * 根据ID查找用户，如果不存在则抛出异常
     *
     * @param userId 用户ID
     * @return 用户实体
     */
    User findByIdOrThrow(String userId);

    /**
     * 修改用户昵称
     *
     * @param userId   用户ID
     * @param nickName 新昵称
     * @return 用户聚合根
     */
    UserAggregate changeNickName(String userId, String nickName);

    /**
     * 冻结用户
     *
     * @param userId 用户ID
     * @return 用户聚合根
     */
    UserAggregate freezeUser(String userId);

    /**
     * 解冻用户
     *
     * @param userId 用户ID
     * @return 用户聚合根
     */
    UserAggregate unfreezeUser(String userId);

    /**
     * 删除用户（软删除）
     *
     * @param userId 用户ID
     * @return 用户聚合根
     */
    UserAggregate deleteUser(String userId);

    /**
     * 更新用户头像
     *
     * @param userId    用户ID
     * @param avatarUrl 头像URL
     * @return 用户聚合根
     */
    UserAggregate updateAvatar(String userId, String avatarUrl);

    /**
     * 验证用户密码
     *
     * @param userId        用户ID
     * @param passwordHash  密码哈希
     * @return 是否验证通过
     */
    boolean verifyPassword(String userId, String passwordHash);

    /**
     * 更新最后登录时间
     *
     * @param userId 用户ID
     * @return 用户聚合根
     */
    UserAggregate updateLastLoginTime(String userId);

    /**
     * 生成邀请码
     *
     * @return 邀请码值对象
     */
    InvitationCode generateInvitationCode();

    /**
     * 验证邀请码有效性
     *
     * @param inviteCode 邀请码
     * @return 是否有效
     */
    boolean validateInvitationCode(String inviteCode);

    /**
     * 根据邀请码查找邀请人
     *
     * @param inviteCode 邀请码
     * @return 邀请人用户实体（如果存在）
     */
    User findInviterByInviteCode(String inviteCode);
}
