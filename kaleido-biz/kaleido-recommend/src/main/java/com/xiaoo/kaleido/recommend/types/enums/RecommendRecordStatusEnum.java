package com.xiaoo.kaleido.recommend.types.enums;

import lombok.Getter;

/**
 * 推荐记录状态枚举
 *
 * @author ouyucheng
 * @date 2026/2/4
 */
@Getter
public enum RecommendRecordStatusEnum {

    /**
     * 处理中 - 已创建推荐记录，正在等待AI处理
     */
    PROCESSING("处理中"),

    /**
     * 已完成 - AI处理成功，已生成穿搭
     */
    COMPLETED("已完成"),

    /**
     * 失败 - AI处理失败
     */
    FAILED("失败");

    /**
     * 状态描述
     */
    private final String description;

    RecommendRecordStatusEnum(String description) {
        this.description = description;
    }

    /**
     * 判断是否为终态（已完成或失败）
     *
     * @return 是否为终态
     */
    public boolean isFinalStatus() {
        return this == COMPLETED || this == FAILED;
    }

    /**
     * 判断是否为处理中状态
     *
     * @return 是否为处理中状态
     */
    public boolean isProcessing() {
        return this == PROCESSING;
    }
}
