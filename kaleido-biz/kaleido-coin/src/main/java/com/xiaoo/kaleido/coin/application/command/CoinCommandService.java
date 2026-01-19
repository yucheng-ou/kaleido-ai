package com.xiaoo.kaleido.coin.application.command;

import com.xiaoo.kaleido.api.coin.command.*;
import com.xiaoo.kaleido.coin.domain.account.adapter.repository.ICoinAccountRepository;
import com.xiaoo.kaleido.coin.domain.account.model.aggregate.CoinAccountAggregate;
import com.xiaoo.kaleido.coin.domain.account.service.ICoinDomainService;
import com.xiaoo.kaleido.coin.domain.account.service.dto.CoinOperationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 金币命令服务
 * <p>
 * 负责编排金币相关的命令操作，包括账户初始化、金币操作等
 * 遵循应用层职责：只负责编排，不包含业务逻辑
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
    public void deposit(DepositCommand command) {
        // 1. 将String类型的bizType转换为枚举
        com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream.BizType bizTypeEnum = 
                com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream.BizType.valueOf(command.getBizType());
        
        // 2. 创建操作DTO
        CoinOperationDTO operationDTO = CoinOperationDTO.createIncome(
                command.getUserId(), 
                command.getAmount(),
                bizTypeEnum,
                command.getBizId(),
                command.getRemark());

        // 3. 调用领域服务增加金币
        CoinAccountAggregate account = coinDomainService.deposit(operationDTO);

        // 4. 保存账户
        coinAccountRepository.update(account);

        // 5. 记录日志
        log.info("应用层：金币增加操作完成，用户ID：{}，金额：{}，业务类型：{}", 
                command.getUserId(), command.getAmount(), command.getBizType());
    }

    /**
     * 减少金币
     *
     * @param command 减少金币命令
     */
    public void withdraw(WithdrawCommand command) {
        // 1. 将String类型的bizType转换为枚举
        com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream.BizType bizTypeEnum = 
                com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream.BizType.valueOf(command.getBizType());
        
        // 2. 创建操作DTO
        CoinOperationDTO operationDTO = CoinOperationDTO.createExpense(
                command.getUserId(), 
                command.getAmount(),
                bizTypeEnum,
                command.getBizId(),
                command.getRemark());

        // 3. 调用领域服务减少金币
        CoinAccountAggregate account = coinDomainService.withdraw(operationDTO);

        // 4. 保存账户
        coinAccountRepository.update(account);

        // 5. 记录日志
        log.info("应用层：金币减少操作完成，用户ID：{}，金额：{}，业务类型：{}", 
                command.getUserId(), command.getAmount(), command.getBizType());
    }
}
