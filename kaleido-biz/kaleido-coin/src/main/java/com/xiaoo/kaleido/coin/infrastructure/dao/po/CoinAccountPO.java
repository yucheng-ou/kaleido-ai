package com.xiaoo.kaleido.coin.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.ds.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 金币账户持久化对象
 * <p>
 * 对应数据库表：t_user_coin_account
 * 存储用户金币账户的基本信息
 *
 * @author ouyucheng
 * @date 2026/1/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user_coin_account")
public class CoinAccountPO extends BasePO {

    /**
     * 用户ID
     * 唯一标识用户，关联用户表
     */
    @TableField("user_id")
    private String userId;

    /**
     * 金币余额
     * 不能为负数
     */
    @TableField("balance")
    private Long balance;

    /**
     * 获取账户描述信息
     *
     * @return 账户描述字符串
     */
    public String getDescription() {
        return String.format("用户ID：%s，余额：%d", userId, balance);
    }
}
