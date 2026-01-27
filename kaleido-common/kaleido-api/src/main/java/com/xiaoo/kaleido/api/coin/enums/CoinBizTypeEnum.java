package com.xiaoo.kaleido.api.coin.enums;

/**
 * 金币业务类型枚举
 * <p>
 * 定义金币流水相关的业务类型，用于标识金币变动的具体业务场景
 *
 * @author ouyucheng
 * @date 2026/1/27
 */
public enum CoinBizTypeEnum {
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
     * 搭配推荐
     */
    OUTFIT_RECOMMEND,

    /**
     * 账户初始化
     */
    INITIAL
}
