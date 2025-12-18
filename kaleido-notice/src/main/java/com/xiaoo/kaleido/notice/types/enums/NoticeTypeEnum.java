package com.xiaoo.kaleido.notice.types.enums;

import lombok.Getter;

/**
 * 通知类型枚举
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Getter
public enum NoticeTypeEnum {
    /**
     * 短信通知
     */
    SMS("短信通知"),

    /**
     * 邮件通知
     */
    EMAIL("邮件通知"),

    /**
     * 微信通知
     */
    WECHAT("微信通知");

    private final String description;

    NoticeTypeEnum(String description) {
        this.description = description;
    }

}
