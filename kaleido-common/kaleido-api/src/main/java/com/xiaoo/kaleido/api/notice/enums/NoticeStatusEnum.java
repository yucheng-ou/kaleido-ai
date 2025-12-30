package com.xiaoo.kaleido.api.notice.enums;

import lombok.Getter;

/**
 * 通知状态枚举
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Getter
public enum NoticeStatusEnum {

    /**
     * 待发送
     */
    PENDING("待发送"),


    /**
     * 发送成功
     */
    SUCCESS("发送成功"),

    /**
     * 发送失败
     */
    FAILED("发送失败");

    private final String description;

    NoticeStatusEnum(String description) {
        this.description = description;
    }

}
