package com.xiaoo.kaleido.api.admin.auth.enums;

import lombok.Getter;

/**
 * 权限类型枚举
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Getter
public enum PermissionType {

    /**
     * 菜单权限
     */
    MENU("MENU", "菜单"),

    /**
     * 按钮权限
     */
    BUTTON("BUTTON", "按钮"),

    /**
     * 接口权限
     */
    API("API", "接口");

    private final String code;
    private final String description;

    PermissionType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码获取权限类型
     *
     * @param code 编码
     * @return 权限类型
     */
    public static PermissionType fromCode(String code) {
        for (PermissionType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的权限类型编码: " + code);
    }

    /**
     * 判断是否为菜单权限
     *
     * @return 是否为菜单权限
     */
    public boolean isMenu() {
        return this == MENU;
    }

    /**
     * 判断是否为按钮权限
     *
     * @return 是否为按钮权限
     */
    public boolean isButton() {
        return this == BUTTON;
    }

    /**
     * 判断是否为接口权限
     *
     * @return 是否为接口权限
     */
    public boolean isApi() {
        return this == API;
    }
}
