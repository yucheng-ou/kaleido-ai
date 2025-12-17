package com.xiaoo.kaleido.notice.domain.constant;

/**
 * 通知类型枚举
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public enum NoticeType {
    /**
     * 短信通知
     */
    SMS("短信通知"),

    /**
     * 邮件通知
     */
    EMAIL("邮件通知"),

    /**
     * 推送通知
     */
    PUSH("推送通知"),

    /**
     * 微信通知
     */
    WECHAT("微信通知");

    private final String description;

    NoticeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
