package com.xiaoo.kaleido.user.domain.adapter.port;

import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;

import java.util.Optional;

/**
 * 用户仓储接口（领域层）
 * 定义用户聚合根的持久化操作
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface UserRepository {

    /**
     * 保存用户聚合根
     *
     * @param userAggregate 用户聚合根
     * @return 保存后的用户聚合根
     */
    UserAggregate save(UserAggregate userAggregate);

    /**
     * 根据ID查找用户聚合根
     *
     * @param id 用户ID
     * @return 用户聚合根（如果存在）
     */
    Optional<UserAggregate> findById(String id);

    /**
     * 根据手机号查找用户聚合根
     *
     * @param telephone 手机号
     * @return 用户聚合根（如果存在）
     */
    Optional<UserAggregate> findByTelephone(String telephone);

    /**
     * 根据邀请码查找用户聚合根
     *
     * @param inviteCode 邀请码
     * @return 用户聚合根（如果存在）
     */
    Optional<UserAggregate> findByInviteCode(String inviteCode);

    /**
     * 检查手机号是否已存在
     *
     * @param telephone 手机号
     * @return 是否存在
     */
    boolean existsByTelephone(String telephone);

    /**
     * 检查邀请码是否已存在
     *
     * @param inviteCode 邀请码
     * @return 是否存在
     */
    boolean existsByInviteCode(String inviteCode);

    /**
     * 检查昵称是否已存在
     *
     * @param nickName 昵称
     * @return 是否存在
     */
    boolean existsByNickName(String nickName);

    /**
     * 根据ID查找用户聚合根，如果不存在则抛出异常
     *
     * @param id 用户ID
     * @return 用户聚合根
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 如果用户不存在
     */
    UserAggregate findByIdOrThrow(String id);

    /**
     * 根据手机号查找用户聚合根，如果不存在则抛出异常
     *
     * @param telephone 手机号
     * @return 用户聚合根
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 如果用户不存在
     */
    UserAggregate findByTelephoneOrThrow(String telephone);

    /**
     * 根据邀请码查找用户聚合根，如果不存在则抛出异常
     *
     * @param inviteCode 邀请码
     * @return 用户聚合根
     * @throws com.xiaoo.kaleido.user.types.exception.UserException 如果用户不存在
     */
    UserAggregate findByInviteCodeOrThrow(String inviteCode);
}
