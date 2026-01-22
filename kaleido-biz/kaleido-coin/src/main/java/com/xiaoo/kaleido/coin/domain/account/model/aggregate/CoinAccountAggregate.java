package com.xiaoo.kaleido.coin.domain.account.model.aggregate;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.coin.domain.account.model.entity.CoinStream;
import com.xiaoo.kaleido.coin.types.exception.CoinErrorCode;
import com.xiaoo.kaleido.coin.types.exception.CoinException;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 金币账户聚合根
 * <p>
 * 管理用户金币账户的核心状态和业务规则，包含账户余额和流水记录
 * 注意：流水记录作为聚合内的实体，没有独立的生命周期
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CoinAccountAggregate extends BaseEntity {

    /**
     * 用户ID
     * 唯一标识用户，关联用户表
     */
    private String userId;

    /**
     * 金币余额
     * 不能为负数
     */
    @Builder.Default
    private Long balance = 0L;

    /**
     * 流水记录列表（聚合根内的实体）
     * 记录账户的所有金币变动流水
     */
    @Builder.Default
    private List<CoinStream> streams = new ArrayList<>();

    /**
     * 创建新账户

     * 用于用户注册时初始化金币账户
     * 注意：参数校验在Service层完成，这里只负责构建聚合根
     *
     * @param userId 用户ID，不能为空
     * @param initialBalance 初始余额，必须大于等于0
     * @return 金币账户聚合根
     */
    public static CoinAccountAggregate create(String userId, Long initialBalance) {
        CoinAccountAggregate account = CoinAccountAggregate.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .userId(userId)
                .balance(initialBalance)
                .build();

        // 创建初始化流水记录
        if (initialBalance > 0) {
            CoinStream initialStream = CoinStream.createIncome(
                    account.getId(),
                    userId,
                    initialBalance,
                    initialBalance,
                    CoinStream.BizType.INITIAL,
                    null,
                    "账户初始化"
            );
            account.getStreams().add(initialStream);
        }

        return account;
    }

    /**
     * 增加金币（收入）

     * 用于邀请奖励等收入场景
     * 注意：参数校验在Service层完成，这里只负责业务逻辑
     *
     * @param amount 收入金额，必须大于0
     * @param bizType 业务类型，不能为空
     * @param bizId 业务ID，可为空
     * @param remark 备注，可为空
     * @return 创建的流水记录
     */
    public CoinStream deposit(Long amount, CoinStream.BizType bizType, String bizId, String remark) {
        // 更新余额
        this.balance += amount;

        // 创建流水记录
        CoinStream stream = CoinStream.createIncome(
                this.getId(),
                this.userId,
                amount,
                this.balance,
                bizType,
                bizId,
                remark
        );
        this.streams.add(stream);

        return stream;
    }

    /**
     * 减少金币（支出）

     * 用于位置创建、搭配创建等支出场景
     * 注意：参数校验在Service层完成，这里只负责业务逻辑
     *
     * @param amount 支出金额，必须大于0
     * @param bizType 业务类型，不能为空
     * @param bizId 业务ID，可为空
     * @param remark 备注，可为空
     * @return 创建的流水记录
     * @throws CoinException 当余额不足时抛出
     */
    public CoinStream withdraw(Long amount, CoinStream.BizType bizType, String bizId, String remark) {
        // 检查余额是否足够
        if (!hasSufficientBalance(amount)) {
            throw CoinException.balanceInsufficient();
        }

        // 更新余额
        this.balance -= amount;

        // 创建流水记录
        CoinStream stream = CoinStream.createExpense(
                this.getId(),
                this.userId,
                amount,
                this.balance,
                bizType,
                bizId,
                remark
        );
        this.streams.add(stream);

        return stream;
    }

    /**
     * 检查余额是否足够

     * 用于业务操作前的余额检查
     *
     * @param amount 需要检查的金额，必须大于0
     * @return true-余额足够，false-余额不足
     */
    public boolean hasSufficientBalance(Long amount) {
        if (amount == null || amount <= 0) {
            return false;
        }
        return this.balance >= amount;
    }

    /**
     * 获取最近N条流水记录

     * 用于查询最近的流水记录
     *
     * @param limit 限制条数，必须大于0
     * @return 流水记录列表
     */
    public List<CoinStream> getRecentStreams(int limit) {
        if (limit <= 0) {
            return new ArrayList<>();
        }

        int startIndex = Math.max(0, this.streams.size() - limit);
        return new ArrayList<>(this.streams.subList(startIndex, this.streams.size()));
    }

    /**
     * 根据业务类型和业务ID查找流水记录

     * 用于防止重复处理同一业务
     *
     * @param bizType 业务类型，不能为空
     * @param bizId 业务ID，不能为空
     * @return 流水记录（如果存在），否则返回Optional.empty()
     */
    public Optional<CoinStream> findStreamByBizTypeAndBizId(CoinStream.BizType bizType, String bizId) {
        if (bizType == null || bizId == null || bizId.trim().isEmpty()) {
            return Optional.empty();
        }

        return this.streams.stream()
                .filter(stream -> bizType.equals(stream.getBizType()) && bizId.equals(stream.getBizId()))
                .findFirst();
    }

    /**
     * 检查是否已处理过指定业务

     * 用于幂等性检查
     *
     * @param bizType 业务类型，不能为空
     * @param bizId 业务ID，不能为空
     * @return true-已处理过，false-未处理过
     */
    public boolean hasProcessedBiz(CoinStream.BizType bizType, String bizId) {
        return findStreamByBizTypeAndBizId(bizType, bizId).isPresent();
    }

    /**
     * 获取账户统计信息
     *
     * @return 账户统计信息字符串
     */
    public String getStatistics() {
        long totalIncome = this.streams.stream()
                .filter(CoinStream::isIncome)
                .mapToLong(CoinStream::getAmount)
                .sum();
        long totalExpense = this.streams.stream()
                .filter(CoinStream::isExpense)
                .mapToLong(CoinStream::getAmount)
                .sum();
        int streamCount = this.streams.size();

        return String.format("账户余额：%d，总收入：%d，总支出：%d，流水记录数：%d",
                this.balance, totalIncome, totalExpense, streamCount);
    }
}
