package com.xiaoo.kaleido.api.notice.enums;

import lombok.Getter;

/**
 * 通知类型枚举
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Getter
public enum BusinessTypeEnum {
    /**
     * 短信通知
     */
    VERIFY_CODE("验证码");

    private final String description;

    BusinessTypeEnum(String description) {
        this.description = description;
    }

}
