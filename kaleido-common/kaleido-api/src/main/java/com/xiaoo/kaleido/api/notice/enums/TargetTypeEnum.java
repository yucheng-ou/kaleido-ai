package com.xiaoo.kaleido.api.notice.enums;

import lombok.Getter;

/**
 * 推送目标类型枚举
 *
 * @author ouyucheng
 * @date 2026/1/10
 */
@Getter
public enum TargetTypeEnum {
    /**
     * 普通用户
     */
    USER("普通用户"),

    /**
     * 管理员
     */
    ADMIN("管理员");
    
    private final String description;
    
    TargetTypeEnum(String description) {
        this.description = description;
    }
}
