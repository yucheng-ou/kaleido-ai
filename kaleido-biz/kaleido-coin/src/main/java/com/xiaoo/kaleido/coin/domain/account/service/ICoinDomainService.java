package com.xiaoo.kaleido.coin.domain.account.service;

import com.xiaoo.kaleido.coin.domain.account.model.aggregate.CoinAccountAggregate;
import com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream;
import com.xiaoo.kaleido.coin.domain.account.service.dto.CoinOperationDTO;

import java.util.List;

/**
 * 金币领域服务接口
 * <p>
 * 处理金币相关的核心业务逻辑，包括账户管理、金币操作、流水记录等
 * 遵循DDD原则：负责参数校验（针对controller未校验部分）+ 业务规则验证 + 聚合根操作
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
public interface ICoinDomainService {

    /**
     * 初始化用户账户
     * <p>
     * 用户注册时调用，创建初始金币账户
     * 注意：用户ID已在controller层校验
     *
     * @param userId 用户ID，不能为空
     * @return 初始化后的账户聚合根
     */
    CoinAccountAggregate initAccount(String userId);

    /**
     * 处理邀请奖励
     * <p>
     * 邀请新用户注册时，给邀请人发放奖励
     * 注意：幂等性检查，避免重复奖励
     *
     * @param inviterUserId 邀请人用户ID，不能为空
     * @param newUserId     新用户ID，不能为空
     * @return 处理后的账户聚合根
     */
    CoinAccountAggregate processInviteReward(String inviterUserId, String newUserId);

    /**
     * 处理位置创建扣费
     * <p>
     * 用户创建存储位置时扣减金币
     * 注意：余额不足检查，幂等性检查
     *
     * @param userId     用户ID，不能为空
     * @param locationId 位置ID，不能为空
     * @return 处理后的账户聚合根
     */
    CoinAccountAggregate processLocationCreation(String userId, String locationId);

    /**
     * 处理搭配创建扣费
     * <p>
     * 用户创建穿搭搭配时扣减金币
     * 注意：余额不足检查，幂等性检查
     *
     * @param userId   用户ID，不能为空
     * @param outfitId 搭配ID，不能为空
     * @return 处理后的账户聚合根
     */
    CoinAccountAggregate processOutfitCreation(String userId, String outfitId);

    /**
     * 增加金币（通用方法）
     * <p>
     * 通用的金币增加操作，支持各种业务场景
     * 注意：参数校验在Service层完成
     *
     * @param operationDTO 操作参数，不能为空
     * @return 处理后的账户聚合根
     */
    CoinAccountAggregate deposit(CoinOperationDTO operationDTO);

    /**
     * 减少金币（通用方法）
     * <p>
     * 通用的金币减少操作，支持各种业务场景
     * 注意：参数校验在Service层完成，包含余额检查
     *
     * @param operationDTO 操作参数，不能为空
     * @return 处理后的账户聚合根
     */
    CoinAccountAggregate withdraw(CoinOperationDTO operationDTO);

    /**
     * 根据用户ID查询账户
     * <p>
     * 查询用户的金币账户信息
     * 注意：如果账户不存在，根据业务规则决定是否创建或抛出异常
     *
     * @param userId 用户ID，不能为空
     * @return 账户聚合根
     */
    CoinAccountAggregate getAccountByUserId(String userId);

    /**
     * 查询账户余额
     * <p>
     * 查询用户的金币余额
     *
     * @param userId 用户ID，不能为空
     * @return 金币余额
     */
    Long getAccountBalance(String userId);

    /**
     * 查询流水记录
     * <p>
     * 查询用户的金币流水记录
     *
     * @param userId 用户ID，不能为空
     * @param limit  限制条数，必须大于0
     * @return 流水记录列表
     */
    List<CoinStream> getStreamRecords(String userId, int limit);

    /**
     * 检查账户是否存在
     *
     * @param userId 用户ID，不能为空
     * @return true-存在，false-不存在
     */
    boolean accountExists(String userId);

    /**
     * 检查余额是否足够
     * <p>
     * 检查用户余额是否足够支付指定金额
     *
     * @param userId 用户ID，不能为空
     * @param amount 需要检查的金额，必须大于0
     * @return true-足够，false-不足
     */
    boolean hasSufficientBalance(String userId, Long amount);

    /**
     * 获取当前配置描述
     *
     * @return 当前配置描述信息
     */
    String getCurrentConfigDescription();
}
