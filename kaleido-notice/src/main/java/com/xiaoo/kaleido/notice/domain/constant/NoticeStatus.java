package com.xiaoo.kaleido.notice.domain.constant;

/**
 * 通知状态枚举
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public enum NoticeStatus {
    /**
     * 待发送
     */
    PENDING("待发送"),

    /**
     * 发送中
     */
    SENDING("发送中"),

    /**
     * 发送成功
     */
    SUCCESS("发送成功"),

    /**
     * 发送失败
     */
    FAILED("发送失败"),

    /**
     * 重试中
     */
    RETRYING("重试中");

    private final String description;

    NoticeStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
