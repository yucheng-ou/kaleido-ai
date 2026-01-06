package com.xiaoo.kaleido.user.domain.adapter.repository;

import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;
import java.util.List;
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
     * 保存用户
     *
     * @param userAggregate 用户聚合根
     */
    void save(UserAggregate userAggregate);

    /**
     * 需改用户信息
     * @param userAggregate 用户聚合根
     */
    void update(UserAggregate userAggregate);


    /**
     * 根据ID查找用户聚合根（命令用途）
     *
     * @param id 用户ID
     * @return 用户聚合根（如果存在）
     */
    Optional<UserAggregate> findById(String id);

    Optional<UserAggregate> findUserAndStreamById(String id);

    /**
     * 根据ID查找用户聚合根，如果不存在则抛出异常（命令用途）
     *
     * @param id 用户ID
     * @return 用户聚合根
     */
    UserAggregate findByIdOrThrow(String id);


    /**
     * 根据手机号查找用户聚合根
     *
     * @param telephone 手机号
     * @return 用户聚合根（如果存在）
     */
    Optional<UserAggregate>  findByTelephone(String telephone);

    /**
     * 根据邀请码查找用户聚合根
     *
     * @param inviteCode 邀请码
     * @return 用户聚合根（如果存在）
     */
    Optional<UserAggregate>  findByInviteCode(String inviteCode);

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
     * 分页查询用户
     *
     * @param req 查询条件
     * @return 用户列表
     */
    List<UserAggregate> pageQuery(UserPageQueryReq req);

}
