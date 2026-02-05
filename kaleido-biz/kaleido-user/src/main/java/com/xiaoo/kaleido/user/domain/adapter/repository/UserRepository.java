package com.xiaoo.kaleido.user.domain.adapter.repository;

import com.xiaoo.kaleido.api.user.query.UserPageQueryReq;
import com.xiaoo.kaleido.user.domain.model.aggregate.UserAggregate;

import java.util.List;
import java.util.Optional;

/**
 * 用户仓储接口（领域层）
 * <p>
 * 定义用户聚合根的持久化操作，包括保存、查询、更新等数据库操作
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
public interface UserRepository {

    /**
     * 保存用户
     * 保存用户聚合根到数据库，如果是新用户则插入，如果是已存在用户则更新
     *
     * @param userAggregate 用户聚合根，不能为空
     */
    void save(UserAggregate userAggregate);

    /**
     * 修改用户信息
     * 更新用户聚合根信息到数据库
     *
     * @param userAggregate 用户聚合根，不能为空
     */
    void update(UserAggregate userAggregate);

    /**
     * 根据ID查找用户聚合根（命令用途）
     * 根据用户ID查询用户聚合根，直接返回聚合对象，用于查询场景
     *
     * @param id 用户ID，不能为空
     * @return 用户聚合根（如果存在），null表示用户不存在
     */
    UserAggregate findById(String id);

    /**
     * 根据ID查找用户聚合根及其操作流水
     * 查询用户聚合根及其相关的操作流水记录，用于需要完整用户信息的场景
     *
     * @param id 用户ID，不能为空
     * @return 用户聚合根及其操作流水（如果存在），null表示用户不存在
     */
    UserAggregate findUserAndStreamById(String id);


    /**
     * 根据手机号查找用户聚合根
     * 根据手机号查询用户聚合根，用于手机号登录等场景
     *
     * @param telephone 手机号，必须符合手机号格式规范
     * @return 用户聚合根（如果存在），null表示用户不存在
     */
    UserAggregate findByTelephone(String telephone);

    /**
     * 根据邀请码查找用户聚合根
     * 根据邀请码查询用户聚合根，用于邀请关系查询等场景
     *
     * @param inviteCode 邀请码，不能为空
     * @return 用户聚合根（如果存在），null表示用户不存在
     */
    UserAggregate findByInviteCode(String inviteCode);

    /**
     * 检查手机号是否已存在
     * 检查指定手机号是否已被注册，用于用户注册时的唯一性校验
     *
     * @param telephone 手机号，必须符合手机号格式规范
     * @return 是否存在，true表示已存在，false表示不存在
     */
    boolean existsByTelephone(String telephone);

    /**
     * 检查邀请码是否已存在
     * 检查指定邀请码是否已存在，用于邀请码生成时的唯一性校验
     *
     * @param inviteCode 邀请码，不能为空
     * @return 是否存在，true表示已存在，false表示不存在
     */
    boolean existsByInviteCode(String inviteCode);

    /**
     * 检查邀请码是否可能已存在（使用布隆过滤器）
     * 使用布隆过滤器快速判断邀请码是否可能已存在，用于优化生成性能
     * 注意：布隆过滤器有误判率，返回false表示一定不存在，返回true表示可能存在
     *
     * @param inviteCode 邀请码，不能为空
     * @return 是否可能已存在，true表示可能存在（需要进一步确认），false表示一定不存在
     */
    boolean mightExistByInviteCode(String inviteCode);

    /**
     * 添加邀请码到布隆过滤器
     * 将新生成的邀请码添加到布隆过滤器，用于后续快速判断
     *
     * @param inviteCode 邀请码，不能为空
     */
    void addToInviteCodeFilter(String inviteCode);

    /**
     * 分页查询用户
     * 根据查询条件分页查询用户列表，支持多种查询条件组合
     *
     * @param req 查询条件，包含分页参数和过滤条件
     * @return 用户列表，按分页参数返回指定页的数据
     */
    List<UserAggregate> pageQuery(UserPageQueryReq req);

}
