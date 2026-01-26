package com.xiaoo.kaleido.api.coin;

import com.xiaoo.kaleido.api.coin.command.*;
import com.xiaoo.kaleido.base.result.Result;
import jakarta.validation.Valid;

/**
 * 金币RPC服务接口
 * <p>
 * 提供金币相关的远程过程调用服务，包括账户初始化、金币操作等功能
 *
 * @author ouyucheng
 * @date 2026/1/23
 * @dubbo
 */
public interface IRpcCoinService {

    /**
     * 初始化用户账户
     * <p>
     * 为用户创建初始的金币账户，并返回创建的账户ID
     *
     * @param userId  用户ID
     * @param command 初始化账户命令，包含用户ID等信息
     * @return 创建的账户ID
     */
    Result<String> initAccount(String userId, @Valid InitAccountCommand command);

    /**
     * 处理邀请奖励
     * <p>
     * 当新用户通过邀请注册时，为邀请人发放邀请奖励
     *
     * @param userId  用户ID（邀请人用户ID）
     * @param command 处理邀请奖励命令，包含邀请人用户ID和新用户ID
     */
    Result<Void> processInviteReward(String userId, @Valid ProcessInviteRewardCommand command);

    /**
     * 处理位置创建扣费
     * <p>
     * 当用户创建存储位置时，扣除相应的金币费用
     *
     * @param userId  用户ID
     * @param command 处理位置创建扣费命令，包含用户ID和位置ID
     */
    Result<Void> processLocationCreation(String userId, @Valid ProcessLocationCreationCommand command);

    /**
     * 处理搭配创建扣费
     * <p>
     * 当用户创建搭配时，扣除相应的金币费用
     *
     * @param userId  用户ID
     * @param command 处理搭配创建扣费命令，包含用户ID和搭配ID
     */
    Result<Void> processOutfitCreation(String userId, @Valid ProcessOutfitCreationCommand command);

    /**
     * 增加金币
     * <p>
     * 为用户账户增加指定数量的金币
     *
     * @param userId  用户ID
     * @param command 增加金币命令，包含用户ID、金额、业务类型等信息
     */
    Result<Void> deposit(String userId, @Valid DepositCommand command);

    /**
     * 减少金币
     * <p>
     * 从用户账户减少指定数量的金币
     *
     * @param userId  用户ID
     * @param command 减少金币命令，包含用户ID、金额、业务类型等信息
     */
    Result<Void> withdraw(String userId, @Valid WithdrawCommand command);

    /**
     * 处理推荐生成扣费
     * <p>
     * 当用户生成AI推荐时，扣除相应的金币费用
     *
     * @param userId  用户ID
     * @param command 处理推荐生成扣费命令，包含用户ID和推荐记录ID
     */
    Result<Void> processRecommendGeneration(String userId, @Valid ProcessRecommendGenerationCommand command);
}
