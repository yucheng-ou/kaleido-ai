package com.xiaoo.kaleido.coin.domain.account.adapter.repository;

import com.xiaoo.kaleido.coin.domain.account.model.aggregate.CoinAccountAggregate;

import java.util.Optional;

/**
 * 金币账户仓储接口
 * <p>
 * 定义金币账户聚合根的持久化操作，包括保存、查询、更新等数据库操作
 * 遵循依赖倒置原则，接口定义在领域层，实现在基础设施层
 * 注意：只提供聚合根级别的操作，需要同时保存账户和流水记录
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
public interface ICoinAccountRepository {

    /**
     * 保存账户聚合根

     * 保存账户聚合根到数据库，如果是新账户则插入，如果是已存在账户则更新
     * 注意：需要同时保存账户基本信息和流水记录列表
     *
     * @param accountAggregate 账户聚合根，不能为空
     */
    void save(CoinAccountAggregate accountAggregate);

    /**
     * 更新账户聚合根

     * 更新账户聚合根信息到数据库
     * 注意：需要同时更新账户基本信息和流水记录列表
     *
     * @param accountAggregate 账户聚合根，不能为空
     */
    void update(CoinAccountAggregate accountAggregate);

    /**
     * 根据ID查找账户聚合根

     * 根据账户ID查询账户聚合根，返回Optional对象
     * 注意：需要同时加载账户的基本信息和流水记录列表
     *
     * @param accountId 账户ID，不能为空
     * @return 账户聚合根（如果存在），Optional.empty()表示账户不存在
     */
    Optional<CoinAccountAggregate> findById(String accountId);

    /**
     * 根据用户ID查找账户聚合根

     * 根据用户ID查询账户聚合根，返回Optional对象
     * 注意：需要同时加载账户的基本信息和流水记录列表
     *
     * @param userId 用户ID，不能为空
     * @return 账户聚合根（如果存在），Optional.empty()表示账户不存在
     */
    Optional<CoinAccountAggregate> findByUserId(String userId);

    /**
     * 根据用户ID查找账户聚合根，如果不存在则抛出异常

     * 用于命令操作中需要确保账户存在的场景，如果账户不存在则抛出异常
     * 注意：需要同时加载账户的基本信息和流水记录列表
     *
     * @param userId 用户ID，不能为空
     * @return 账户聚合根
     */
    CoinAccountAggregate findByUserIdOrThrow(String userId);

    /**
     * 检查账户是否存在

     * 检查指定用户ID的账户是否存在
     *
     * @param userId 用户ID，不能为空
     * @return true-存在，false-不存在
     */
    boolean existsByUserId(String userId);

    /**
     * 删除账户

     * 删除指定账户（逻辑删除或物理删除，根据业务规则）
     *
     * @param accountId 账户ID，不能为空
     */
    void delete(String accountId);

    /**
     * 根据用户ID删除账户

     * 删除指定用户的账户
     *
     * @param userId 用户ID，不能为空
     */
    void deleteByUserId(String userId);

    /**
     * 检查业务是否已处理

     * 检查指定业务类型和业务ID是否已处理过（用于幂等性检查）
     *
     * @param bizType 业务类型，不能为空
     * @param bizId   业务ID，不能为空
     * @return true-已处理过，false-未处理过
     */
    boolean existsByBizTypeAndBizId(String bizType, String bizId);
}
