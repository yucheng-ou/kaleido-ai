package com.xiaoo.kaleido.coin.domain.account.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.coin.types.exception.CoinErrorCode;
import com.xiaoo.kaleido.coin.types.exception.CoinException;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 金币流水实体
 * <p>
 * 作为CoinAccountAggregate聚合根的内部实体，记录金币变动流水
 * 注意：流水实体没有独立的生命周期，必须通过聚合根进行操作
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CoinStream extends BaseEntity {

    /**
     * 流水类型枚举
     */
    public enum StreamType {
        /**
         * 收入
         */
        INCOME,

        /**
         * 支出
         */
        EXPENSE
    }

    /**
     * 业务类型枚举
     */
    public enum BizType {
        /**
         * 邀请奖励
         */
        INVITE,

        /**
         * 位置创建
         */
        LOCATION,

        /**
         * 搭配创建
         */
        OUTFIT,

        /**
         * 账户初始化
         */
        INITIAL
    }

    /**
     * 账户ID
     * 关联CoinAccountAggregate.id
     */
    private String accountId;

    /**
     * 用户ID
     * 冗余存储，便于查询
     */
    private String userId;

    /**
     * 流水类型
     * INCOME-收入, EXPENSE-支出
     */
    private StreamType type;

    /**
     * 变动金额
     * 正数表示收入，负数表示支出
     */
    private Long amount;

    /**
     * 变动后余额
     */
    private Long balanceAfter;

    /**
     * 业务类型
     * INVITE-邀请, LOCATION-位置, OUTFIT-搭配, INITIAL-初始化
     */
    private BizType bizType;

    /**
     * 业务ID
     * 关联具体业务记录ID
     */
    private String bizId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建收入流水

     * 用于记录金币收入场景
     *
     * @param accountId    账户ID，不能为空
     * @param userId       用户ID，不能为空
     * @param amount       收入金额，必须大于0
     * @param balanceAfter 变动后余额
     * @param bizType      业务类型，不能为空
     * @param bizId        业务ID，可为空
     * @param remark       备注，可为空
     * @return 收入流水实体
     * @throws CoinException 当参数无效时抛出
     */
    public static CoinStream createIncome(
            String accountId,
            String userId,
            Long amount,
            Long balanceAfter,
            BizType bizType,
            String bizId,
            String remark) {
        // 参数校验
        if (accountId == null || accountId.trim().isEmpty()) {
            throw CoinException.paramNotNull("账户ID");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }
        if (amount == null || amount <= 0) {
            throw CoinException.of(CoinErrorCode.STREAM_AMOUNT_INVALID, "收入金额必须大于0");
        }
        if (bizType == null) {
            throw CoinException.of(CoinErrorCode.STREAM_BIZ_TYPE_INVALID, "业务类型不能为空");
        }

        return CoinStream.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .accountId(accountId)
                .userId(userId)
                .type(StreamType.INCOME)
                .amount(amount)
                .balanceAfter(balanceAfter)
                .bizType(bizType)
                .bizId(bizId)
                .remark(remark)
                .build();
    }

    /**
     * 创建支出流水

     * 用于记录金币支出场景
     *
     * @param accountId    账户ID，不能为空
     * @param userId       用户ID，不能为空
     * @param amount       支出金额，必须大于0
     * @param balanceAfter 变动后余额
     * @param bizType      业务类型，不能为空
     * @param bizId        业务ID，可为空
     * @param remark       备注，可为空
     * @return 支出流水实体
     * @throws CoinException 当参数无效时抛出
     */
    public static CoinStream createExpense(
            String accountId,
            String userId,
            Long amount,
            Long balanceAfter,
            BizType bizType,
            String bizId,
            String remark) {
        // 参数校验
        if (accountId == null || accountId.trim().isEmpty()) {
            throw CoinException.paramNotNull("账户ID");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw CoinException.paramNotNull("用户ID");
        }
        if (amount == null || amount <= 0) {
            throw CoinException.of(CoinErrorCode.STREAM_AMOUNT_INVALID, "支出金额必须大于0");
        }
        if (bizType == null) {
            throw CoinException.of(CoinErrorCode.STREAM_BIZ_TYPE_INVALID, "业务类型不能为空");
        }

        return CoinStream.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .accountId(accountId)
                .userId(userId)
                .type(StreamType.EXPENSE)
                .amount(amount)
                .balanceAfter(balanceAfter)
                .bizType(bizType)
                .bizId(bizId)
                .remark(remark)
                .build();
    }

    /**
     * 检查是否为收入流水
     *
     * @return true-收入流水，false-支出流水
     */
    public boolean isIncome() {
        return StreamType.INCOME.equals(this.type);
    }

    /**
     * 检查是否为支出流水
     *
     * @return true-支出流水，false-收入流水
     */
    public boolean isExpense() {
        return StreamType.EXPENSE.equals(this.type);
    }

    /**
     * 获取流水描述
     *
     * @return 流水描述字符串
     */
    public String getDescription() {
        String typeDesc = isIncome() ? "收入" : "支出";
        String bizDesc = getBizTypeDescription();
        return String.format("%s %d 金币，业务类型：%s", typeDesc, Math.abs(this.amount), bizDesc);
    }

    /**
     * 获取业务类型描述
     *
     * @return 业务类型描述
     */
    private String getBizTypeDescription() {
        if (bizType == null) {
            return "未知";
        }
        return switch (bizType) {
            case INVITE -> "邀请奖励";
            case LOCATION -> "位置创建";
            case OUTFIT -> "搭配创建";
            case INITIAL -> "账户初始化";
        };
    }
}
