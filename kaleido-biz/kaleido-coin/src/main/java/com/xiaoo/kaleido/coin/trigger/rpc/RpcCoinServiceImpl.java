package com.xiaoo.kaleido.coin.trigger.rpc;

import com.xiaoo.kaleido.api.coin.IRpcCoinService;
import com.xiaoo.kaleido.api.coin.command.*;
import com.xiaoo.kaleido.base.result.Result;
import com.xiaoo.kaleido.coin.application.command.CoinCommandService;
import com.xiaoo.kaleido.rpc.constant.RpcConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 金币RPC服务实现
 *
 * @author ouyucheng
 * @date 2026/1/23
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@DubboService(version = RpcConstants.DUBBO_VERSION)
public class RpcCoinServiceImpl implements IRpcCoinService {

    private final CoinCommandService coinCommandService;

    @Override
    public Result<String> initAccount(String userId, @Valid InitAccountCommand command) {
        log.info("RPC服务：初始化用户账户，用户ID：{}", userId);
        String accountId = coinCommandService.initAccount(command);
        return Result.success(accountId);
    }

    @Override
    public Result<Void> processInviteReward(String userId, @Valid ProcessInviteRewardCommand command) {
        log.info("RPC服务：处理邀请奖励，邀请人用户ID：{}，新用户ID：{}", 
                command.getInviterUserId(), command.getNewUserId());
        coinCommandService.processInviteReward(command);
        return Result.success();
    }

    @Override
    public Result<Void> processLocationCreation(String userId, @Valid ProcessLocationCreationCommand command) {
        log.info("RPC服务：处理位置创建扣费，用户ID：{}，位置ID：{}", 
                command.getUserId(), command.getLocationId());
        coinCommandService.processLocationCreation(command);
        return Result.success();
    }

    @Override
    public Result<Void> processOutfitCreation(String userId, @Valid ProcessOutfitCreationCommand command) {
        log.info("RPC服务：处理搭配创建扣费，用户ID：{}，搭配ID：{}", 
                command.getUserId(), command.getOutfitId());
        coinCommandService.processOutfitCreation(command);
        return Result.success();
    }

    @Override
    public Result<Void> deposit(String userId, @Valid DepositCommand command) {
        log.info("RPC服务：增加金币，用户ID：{}，金额：{}，业务类型：{}", 
                command.getUserId(), command.getAmount(), command.getBizType());
        coinCommandService.deposit(command);
        return Result.success();
    }

    @Override
    public Result<Void> withdraw(String userId, @Valid WithdrawCommand command) {
        log.info("RPC服务：减少金币，用户ID：{}，金额：{}，业务类型：{}", 
                command.getUserId(), command.getAmount(), command.getBizType());
        coinCommandService.withdraw(command);
        return Result.success();
    }

    @Override
    public Result<Void> processRecommendGeneration(String userId, @Valid ProcessRecommendGenerationCommand command) {
        log.info("RPC服务：处理推荐生成扣费，用户ID：{}，推荐记录ID：{}", 
                command.getUserId(), command.getRecommendRecordId());
        coinCommandService.processRecommendGeneration(command);
        return Result.success();
    }
}
