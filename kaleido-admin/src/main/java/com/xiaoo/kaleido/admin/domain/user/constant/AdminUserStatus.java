package com.xiaoo.kaleido.admin.domain.user.constant;

import lombok.Getter;

/**
 * 管理员状态枚举
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Getter
public enum AdminUserStatus {

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 冻结
     */
    FROZEN(2, "冻结"),

    /**
     * 删除
     */
    DELETED(3, "删除");

    private final Integer code;
    private final String description;

    AdminUserStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码获取管理员状态
     *
     * @param code 编码
     * @return 管理员状态
     */
    public static AdminUserStatus fromCode(Integer code) {
        for (AdminUserStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的管理员状态编码: " + code);
    }


    /**
     * 判断是否为正常状态
     *
     * @return 是否为正常状态
     */
    public boolean isNormal() {
        return this == NORMAL;
    }

    /**
     * 判断是否为冻结状态
     *
     * @return 是否为冻结状态
     */
    public boolean isFrozen() {
        return this == FROZEN;
    }

    /**
     * 判断是否为删除状态
     *
     * @return 是否为删除状态
     */
    public boolean isDeleted() {
        return this == DELETED;
    }

    /**
     * 判断管理员是否可用
     *
     * @return 是否可用
     */
    public boolean isAvailable() {
        return this == NORMAL;
    }

    /**
     * 判断管理员是否可操作
     *
     * @return 是否可操作
     */
    public boolean isOperable() {
        return this == NORMAL || this == FROZEN;
    }
}
