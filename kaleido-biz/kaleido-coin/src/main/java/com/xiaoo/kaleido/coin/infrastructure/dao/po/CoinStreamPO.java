package com.xiaoo.kaleido.coin.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 金币流水持久化对象
 * <p>
 * 对应数据库表：t_user_coin_stream
 * 存储用户金币变动流水记录
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user_coin_stream")
public class CoinStreamPO extends BaseEntity {

    /**
     * 用户ID
     * 冗余存储，便于查询
     */
    @TableField("user_id")
    private String userId;

    /**
     * 流水类型
     * INCOME-收入, EXPENSE-支出
     */
    @TableField("type")
    private String type;

    /**
     * 变动金额
     * 正数表示收入，负数表示支出
     */
    @TableField("amount")
    private Long amount;

    /**
     * 变动后余额
     */
    @TableField("balance_after")
    private Long balanceAfter;

    /**
     * 业务类型
     * INVITE-邀请, LOCATION-位置, OUTFIT-搭配, INITIAL-初始化
     */
    @TableField("biz_type")
    private String bizType;

    /**
     * 业务ID
     * 关联具体业务记录ID
     */
    @TableField("biz_id")
    private String bizId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 检查是否为收入流水
     *
     * @return true-收入流水，false-支出流水
     */
    public boolean isIncome() {
        return "INCOME".equals(this.type);
    }

    /**
     * 检查是否为支出流水
     *
     * @return true-支出流水，false-收入流水
     */
    public boolean isExpense() {
        return "EXPENSE".equals(this.type);
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
            case "INVITE" -> "邀请奖励";
            case "LOCATION" -> "位置创建";
            case "OUTFIT" -> "搭配创建";
            case "INITIAL" -> "账户初始化";
            default -> "未知";
        };
    }
}
