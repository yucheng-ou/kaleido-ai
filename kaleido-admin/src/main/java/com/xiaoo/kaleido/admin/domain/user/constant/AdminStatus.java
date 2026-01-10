package com.xiaoo.kaleido.admin.domain.user.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 管理员状态枚举
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Getter
public enum AdminStatus {

    /**
     * 正常
     */
    NORMAL("NORMAL", "正常"),

    /**
     * 冻结
     */
    FROZEN("FROZEN", "冻结"),

    /**
     * 删除
     */
    DELETED("DELETED", "删除");

    @EnumValue
    private final String code;
    private final String description;

    AdminStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码获取管理员状态
     *
     * @param code 编码
     * @return 管理员状态
     * @throws IllegalArgumentException 如果编码不存在
     */
    public static AdminStatus fromCode(String code) {
        for (AdminStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的管理员状态编码: " + code);
    }

    /**
     * 根据编码安全地获取管理员状态，如果编码不存在则返回null
     *
     * @param code 编码
     * @return 管理员状态，如果编码不存在则返回null
     */
    public static AdminStatus fromCodeSafe(String code) {
        if (code == null) {
            return null;
        }
        for (AdminStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 根据编码获取管理员状态，如果编码不存在则返回默认值
     *
     * @param code 编码
     * @param defaultValue 默认值
     * @return 管理员状态，如果编码不存在则返回默认值
     */
    public static AdminStatus fromCodeOrDefault(String code, AdminStatus defaultValue) {
        if (code == null) {
            return defaultValue;
        }
        for (AdminStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return defaultValue;
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
