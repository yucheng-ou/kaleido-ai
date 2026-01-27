package com.xiaoo.kaleido.coin.application.command;

import com.xiaoo.kaleido.api.coin.command.*;
import com.xiaoo.kaleido.api.coin.enums.CoinBizTypeEnum;
import com.xiaoo.kaleido.coin.domain.account.adapter.repository.ICoinAccountRepository;
import com.xiaoo.kaleido.coin.domain.account.model.aggregate.CoinAccountAggregate;
import com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream;
import com.xiaoo.kaleido.coin.domain.account.service.ICoinDomainService;
import com.xiaoo.kaleido.coin.domain.account.service.dto.CoinOperationDTO;
import com.xiaoo.kaleido.lock.annotation.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 金币命令服务
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CoinCommandService {

    private final ICoinDomainService coinDomainService;
    private final ICoinAccountRepository coinAccountRepository;

    /**
     * 初始化用户账户
     *
     * @param command 初始化账户命令
     * @return 创建的账户ID
     */
    @DistributedLock(key = "'coin:stream:' + #userId")
    public String initAccount(InitAccountCommand command) {
        // 1. 调用领域服务创建账户聚合根
        CoinAccountAggregate account = coinDomainService.initAccount(command.getUserId());

        // 2. 保存账户
        coinAccountRepository.save(account);

        // 3. 记录日志
        log.info("应用层：用户账户初始化完成，用户ID：{}，账户ID：{}", command.getUserId(), account.getId());

        return account.getId();
    }

    /**
     * 处理邀请奖励
     *
     * @param command 处理邀请奖励命令
     */
    @DistributedLock(key = "'coin:stream:' + #userId")
    public void processInviteReward(ProcessInviteRewardCommand command) {
        // 1. 调用领域服务处理邀请奖励
        CoinAccountAggregate account = coinDomainService.processInviteReward(
                command.getInviterUserId(), command.getNewUserId());

        // 2. 保存账户
        coinAccountRepository.update(account);

        // 3. 记录日志
        log.info("应用层：邀请奖励处理完成，邀请人用户ID：{}，新用户ID：{}",
                command.getInviterUserId(), command.getNewUserId());
    }

    /**
     * 处理位置创建扣费
     *
     * @param command 处理位置创建扣费命令
     */
    @DistributedLock(key = "'coin:stream:' + #userId")
    public void processLocationCreation(ProcessLocationCreationCommand command) {
        // 1. 调用领域服务处理位置创建扣费
        CoinAccountAggregate account = coinDomainService.processLocationCreation(
                command.getUserId(), command.getLocationId());

        // 2. 保存账户
        coinAccountRepository.update(account);

        // 3. 记录日志
        log.info("应用层：位置创建扣费处理完成，用户ID：{}，位置ID：{}",
                command.getUserId(), command.getLocationId());
    }

    /**
     * 处理搭配创建扣费
     *
     * @param command 处理搭配创建扣费命令
     */
    @DistributedLock(key = "'coin:stream:' + #userId")
    public void processOutfitCreation(ProcessOutfitCreationCommand command) {
        // 1. 调用领域服务处理搭配创建扣费
        CoinAccountAggregate account = coinDomainService.processOutfitCreation(
                command.getUserId(), command.getOutfitId());

        // 2. 保存账户
        coinAccountRepository.update(account);

        // 3. 记录日志
        log.info("应用层：搭配创建扣费处理完成，用户ID：{}，搭配ID：{}",
                command.getUserId(), command.getOutfitId());
    }

    /**
     * 增加金币
     *
     * @param command 增加金币命令
     */
    @DistributedLock(key = "'coin:stream:' + #userId")
    public void deposit(DepositCommand command) {
        // 1. 创建操作DTO
        CoinOperationDTO operationDTO = CoinOperationDTO.createIncome(
                command.getUserId(),
                command.getAmount(),
                command.getBizType(),
                command.getBizId(),
                command.getRemark());

        // 2. 调用领域服务增加金币
        CoinAccountAggregate account = coinDomainService.deposit(operationDTO);

        // 3. 保存账户
        coinAccountRepository.update(account);

        // 4. 记录日志
        log.info("应用层：金币增加操作完成，用户ID：{}，金额：{}，业务类型：{}",
                command.getUserId(), command.getAmount(), command.getBizType());
    }

    /**
     * 减少金币
     *
     * @param command 减少金币命令
     */
    @DistributedLock(key = "'coin:stream:' + #userId")
    public void withdraw(WithdrawCommand command) {
        // 1. 创建操作DTO
        CoinOperationDTO operationDTO = CoinOperationDTO.createExpense(
                command.getUserId(),
                command.getAmount(),
                command.getBizType(),
                command.getBizId(),
                command.getRemark());

        // 2. 调用领域服务减少金币
        CoinAccountAggregate account = coinDomainService.withdraw(operationDTO);

        // 3. 保存账户
        coinAccountRepository.update(account);

        // 4. 记录日志
        log.info("应用层：金币减少操作完成，用户ID：{}，金额：{}，业务类型：{}",
                command.getUserId(), command.getAmount(), command.getBizType());
    }

    /**
     * 处理推荐生成扣费
     *
     * @param userId            用户id
     * @param recommendRecordId 推荐记录id
     */
    @DistributedLock(key = "'coin:stream:' + #userId")
    public void processRecommendGeneration(String userId, String recommendRecordId) {
        // 1. 调用领域服务处理推荐生成扣费
        CoinAccountAggregate account = coinDomainService.processOutfitRecommendGeneration(userId, recommendRecordId);

        // 2. 保存账户
        coinAccountRepository.update(account);

        // 3. 记录日志
        log.info("应用层：推荐生成扣费处理完成，用户ID：{}，推荐记录ID：{}", userId, recommendRecordId);
    }

    /**
     * 校验金币是否足够
     *
     * @param userId  用户ID
     * @param bizType 业务类型
     * @return 是否足够
     */
    public boolean checkBalance(String userId, CoinBizTypeEnum bizType) {
        // 1. 调用领域服务检查余额是否足够
        boolean sufficient = coinDomainService.hasSufficientBalanceForBizType(userId, bizType);

        // 2. 记录日志
        log.info("应用层：校验金币是否足够，用户ID：{}，业务类型：{}，结果：{}", userId, bizType, sufficient);

        return sufficient;
    }
}
