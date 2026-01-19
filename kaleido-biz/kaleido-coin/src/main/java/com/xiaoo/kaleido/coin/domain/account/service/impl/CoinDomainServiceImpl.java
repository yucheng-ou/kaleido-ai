package com.xiaoo.kaleido.coin.domain.account.service.impl;

import com.xiaoo.kaleido.coin.domain.account.adapter.repository.ICoinAccountRepository;
import com.xiaoo.kaleido.coin.domain.account.model.aggregate.CoinAccountAggregate;
import com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream;
import com.xiaoo.kaleido.coin.domain.account.service.ICoinDomainService;
import com.xiaoo.kaleido.coin.domain.account.service.dto.CoinOperationDTO;
import com.xiaoo.kaleido.coin.infrastructure.adapter.rpc.CoinDictConfigService;
import com.xiaoo.kaleido.coin.types.exception.CoinException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 金币领域服务实现类
 * <p>
 * 实现金币领域服务的所有业务逻辑
 * 遵循DDD原则：负责参数校验（针对controller未校验部分）+ 业务规则验证 + 聚合根操作
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CoinDomainServiceImpl implements ICoinDomainService {

    private final ICoinAccountRepository coinAccountRepository;
    private final CoinDictConfigService coinDictConfigService;

    @Override
    public CoinAccountAggregate initAccount(String userId) {
        // 1. 参数校验（针对controller未校验部分）
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }

        // 2. 验证配置
        coinDictConfigService.validateConfig();

        // 3. 检查账户是否已存在
        if (coinAccountRepository.existsByUserId(userId)) {
            throw CoinException.dataAlreadyExists("用户账户已存在");
        }

        // 4. 获取初始余额
        Long initialBalance = coinDictConfigService.getInitialBalance();

        // 5. 创建账户聚合根
        CoinAccountAggregate account = CoinAccountAggregate.create(userId, initialBalance);

        // 6. 记录日志
        log.info("初始化用户账户成功，用户ID：{}，初始余额：{}", userId, initialBalance);
        return account;
    }

    @Override
    public CoinAccountAggregate processInviteReward(String inviterUserId, String newUserId) {
        // 1. 参数校验（针对controller未校验部分）
        if (inviterUserId == null || inviterUserId.trim().isEmpty()) {
            throw CoinException.paramNotNull("邀请人用户ID");
        }
        if (newUserId == null || newUserId.trim().isEmpty()) {
            throw CoinException.paramNotNull("新用户ID");
        }

        // 2. 验证配置
        coinDictConfigService.validateConfig();

        // 3. 幂等性检查：检查是否已处理过该邀请
        if (coinAccountRepository.existsByBizTypeAndBizId(
                CoinStream.BizType.INVITE.name(), newUserId)) {
            log.warn("邀请奖励已处理过，邀请人用户ID：{}，新用户ID：{}", inviterUserId, newUserId);
            return coinAccountRepository.findByUserIdOrThrow(inviterUserId);
        }

        // 4. 获取邀请奖励金额
        Long inviteReward = coinDictConfigService.getInviteReward();

        // 5. 获取邀请人账户
        CoinAccountAggregate account = coinAccountRepository.findByUserIdOrThrow(inviterUserId);

        // 6. 发放邀请奖励
        CoinStream stream = account.deposit(
                inviteReward,
                CoinStream.BizType.INVITE,
                newUserId,
                "邀请新用户注册奖励"
        );

        // 7. 记录日志
        log.info("处理邀请奖励成功，邀请人用户ID：{}，新用户ID：{}，奖励金额：{}",
                inviterUserId, newUserId, inviteReward);
        return account;
    }

    @Override
    public CoinAccountAggregate processLocationCreation(String userId, String locationId) {
        // 1. 参数校验（针对controller未校验部分）
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }
        if (locationId == null || locationId.trim().isEmpty()) {
            throw CoinException.paramNotNull("位置ID");
        }

        // 2. 验证配置
        coinDictConfigService.validateConfig();

        // 3. 幂等性检查：检查是否已处理过该位置创建
        if (coinAccountRepository.existsByBizTypeAndBizId(
                CoinStream.BizType.LOCATION.name(), locationId)) {
            log.warn("位置创建扣费已处理过，用户ID：{}，位置ID：{}", userId, locationId);
            return coinAccountRepository.findByUserIdOrThrow(userId);
        }

        // 4. 获取位置创建消耗金额
        Long locationCost = coinDictConfigService.getLocationCost();

        // 5. 获取用户账户
        CoinAccountAggregate account = coinAccountRepository.findByUserIdOrThrow(userId);

        // 6. 检查余额是否足够
        if (!account.hasSufficientBalance(locationCost)) {
            throw CoinException.balanceInsufficient();
        }

        // 7. 扣减金币
        CoinStream stream = account.withdraw(
                locationCost,
                CoinStream.BizType.LOCATION,
                locationId,
                "创建存储位置消耗"
        );

        // 8. 记录日志
        log.info("处理位置创建扣费成功，用户ID：{}，位置ID：{}，消耗金额：{}",
                userId, locationId, locationCost);
        return account;
    }

    @Override
    public CoinAccountAggregate processOutfitCreation(String userId, String outfitId) {
        // 1. 参数校验（针对controller未校验部分）
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }
        if (outfitId == null || outfitId.trim().isEmpty()) {
            throw CoinException.paramNotNull("搭配ID");
        }

        // 2. 验证配置
        coinDictConfigService.validateConfig();

        // 3. 幂等性检查：检查是否已处理过该搭配创建
        if (coinAccountRepository.existsByBizTypeAndBizId(
                CoinStream.BizType.OUTFIT.name(), outfitId)) {
            log.warn("搭配创建扣费已处理过，用户ID：{}，搭配ID：{}", userId, outfitId);
            return coinAccountRepository.findByUserIdOrThrow(userId);
        }

        // 4. 获取搭配创建消耗金额
        Long outfitCost = coinDictConfigService.getOutfitCost();

        // 5. 获取用户账户
        CoinAccountAggregate account = coinAccountRepository.findByUserIdOrThrow(userId);

        // 6. 检查余额是否足够
        if (!account.hasSufficientBalance(outfitCost)) {
            throw CoinException.balanceInsufficient();
        }

        // 7. 扣减金币
        CoinStream stream = account.withdraw(
                outfitCost,
                CoinStream.BizType.OUTFIT,
                outfitId,
                "创建穿搭搭配消耗"
        );

        // 8. 记录日志
        log.info("处理搭配创建扣费成功，用户ID：{}，搭配ID：{}，消耗金额：{}",
                userId, outfitId, outfitCost);
        return account;
    }

    @Override
    public CoinAccountAggregate deposit(CoinOperationDTO operationDTO) {
        // 1. 参数校验
        if (operationDTO == null || !operationDTO.isValid()) {
            throw CoinException.paramError("操作参数无效");
        }

        // 2. 幂等性检查（如果提供了业务ID）
        if (operationDTO.getBizId() != null && !operationDTO.getBizId().trim().isEmpty()) {
            if (coinAccountRepository.existsByBizTypeAndBizId(
                    operationDTO.getBizType().name(), operationDTO.getBizId())) {
                log.warn("金币增加操作已处理过，业务类型：{}，业务ID：{}",
                        operationDTO.getBizType(), operationDTO.getBizId());
                return coinAccountRepository.findByUserIdOrThrow(operationDTO.getUserId());
            }
        }

        // 3. 获取用户账户
        CoinAccountAggregate account = coinAccountRepository.findByUserIdOrThrow(operationDTO.getUserId());

        // 4. 增加金币
        CoinStream stream = account.deposit(
                operationDTO.getAmount(),
                operationDTO.getBizType(),
                operationDTO.getBizId(),
                operationDTO.getRemark()
        );

        // 5. 记录日志
        log.info("金币增加操作成功，{}", operationDTO.getDescription());
        return account;
    }

    @Override
    public CoinAccountAggregate withdraw(CoinOperationDTO operationDTO) {
        // 1. 参数校验
        if (operationDTO == null || !operationDTO.isValid()) {
            throw CoinException.paramError("操作参数无效");
        }

        // 2. 幂等性检查（如果提供了业务ID）
        if (operationDTO.getBizId() != null && !operationDTO.getBizId().trim().isEmpty()) {
            if (coinAccountRepository.existsByBizTypeAndBizId(
                    operationDTO.getBizType().name(), operationDTO.getBizId())) {
                log.warn("金币减少操作已处理过，业务类型：{}，业务ID：{}",
                        operationDTO.getBizType(), operationDTO.getBizId());
                return coinAccountRepository.findByUserIdOrThrow(operationDTO.getUserId());
            }
        }

        // 3. 获取用户账户
        CoinAccountAggregate account = coinAccountRepository.findByUserIdOrThrow(operationDTO.getUserId());

        // 4. 检查余额是否足够
        if (!account.hasSufficientBalance(operationDTO.getAmount())) {
            throw CoinException.balanceInsufficient();
        }

        // 5. 减少金币
        CoinStream stream = account.withdraw(
                operationDTO.getAmount(),
                operationDTO.getBizType(),
                operationDTO.getBizId(),
                operationDTO.getRemark()
        );

        // 6. 记录日志
        log.info("金币减少操作成功，{}", operationDTO.getDescription());
        return account;
    }

    @Override
    public CoinAccountAggregate getAccountByUserId(String userId) {
        // 1. 参数校验
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }

        // 2. 查询账户
        return coinAccountRepository.findByUserIdOrThrow(userId);
    }

    @Override
    public Long getAccountBalance(String userId) {
        // 1. 参数校验
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }

        // 2. 查询账户
        CoinAccountAggregate account = coinAccountRepository.findByUserIdOrThrow(userId);
        
        // 3. 返回余额
        return account.getBalance();
    }

    @Override
    public List<CoinStream> getStreamRecords(String userId, int limit) {
        // 1. 参数校验
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }
        if (limit <= 0) {
            throw CoinException.paramError("限制条数必须大于0");
        }

        // 2. 查询账户
        CoinAccountAggregate account = coinAccountRepository.findByUserIdOrThrow(userId);
        
        // 3. 返回流水记录
        return account.getRecentStreams(limit);
    }

    @Override
    public boolean accountExists(String userId) {
        // 1. 参数校验
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }

        // 2. 检查账户是否存在
        return coinAccountRepository.existsByUserId(userId);
    }

    @Override
    public boolean hasSufficientBalance(String userId, Long amount) {
        // 1. 参数校验
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }
        if (amount == null || amount <= 0) {
            throw CoinException.paramError("金额必须大于0");
        }

        // 2. 查询账户
        CoinAccountAggregate account = coinAccountRepository.findByUserIdOrThrow(userId);
        
        // 3. 检查余额是否足够
        return account.hasSufficientBalance(amount);
    }

    @Override
    public String getCurrentConfigDescription() {
        return coinDictConfigService.getDescription();
    }
}
