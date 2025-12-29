package com.xiaoo.kaleido.user.domain.constant;


import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Getter
public enum UserStatus {

    /**
     * 正常状态
     */
    ACTIVE(1, "正常"),

    /**
     * 冻结状态
     */
    FROZEN(2, "冻结"),

    /**
     * 已删除状态（软删除）
     */
    DELETED(3, "已删除");

    private final Integer code;
    private final String desc;

    UserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static UserStatus fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断是否为活跃状态
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * 判断是否为冻结状态
     */
    public boolean isFrozen() {
        return this == FROZEN;
    }

    /**
     * 判断是否为删除状态
     */
    public boolean isDeleted() {
        return this == DELETED;
    }

    /**
     * 判断是否可操作（非删除状态）
     */
    public boolean isOperable() {
        return this != DELETED;
    }

    /**
     * 判断是否可修改信息（活跃状态）
     */
    public boolean canModify() {
        return this == ACTIVE;
    }
}
