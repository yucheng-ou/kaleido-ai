package com.xiaoo.kaleido.api.admin.user.enums;

import lombok.Getter;

/**
 * 管理员权限枚举
 * 格式：模块:资源:操作
 * 
 * @author 系统生成
 * @since 1.0.0
 */
@Getter
public enum AdminPermissionEnum {
    
    // ========== 用户管理权限 ==========
    /** 创建管理员 */
    ADMIN_USER_CREATE("admin:user:create", "创建管理员"),
    
    /** 查看管理员 */
    ADMIN_USER_READ("admin:user:read", "查看管理员"),
    
    /** 修改管理员 */
    ADMIN_USER_UPDATE("admin:user:update", "修改管理员"),
    
    /** 删除管理员 */
    ADMIN_USER_DELETE("admin:user:delete", "删除管理员"),
    
    /** 冻结管理员 */
    ADMIN_USER_FREEZE("admin:user:freeze", "冻结管理员"),
    
    /** 启用管理员 */
    ADMIN_USER_ENABLE("admin:user:enable", "启用管理员"),
    
    /** 分配角色给管理员 */
    ADMIN_USER_ASSIGN_ROLES("admin:user:assign-roles", "分配角色给管理员"),
    
    // ========== 角色管理权限 ==========
    /** 创建角色 */
    ADMIN_ROLE_CREATE("admin:role:create", "创建角色"),
    
    /** 查看角色 */
    ADMIN_ROLE_READ("admin:role:read", "查看角色"),
    
    /** 修改角色 */
    ADMIN_ROLE_UPDATE("admin:role:update", "修改角色"),
    
    /** 删除角色 */
    ADMIN_ROLE_DELETE("admin:role:delete", "删除角色"),
    
    /** 启用角色 */
    ADMIN_ROLE_ENABLE("admin:role:enable", "启用角色"),
    
    /** 禁用角色 */
    ADMIN_ROLE_DISABLE("admin:role:disable", "禁用角色"),
    
    /** 分配权限给角色 */
    ADMIN_ROLE_ASSIGN_PERMISSIONS("admin:role:assign-permissions", "分配权限给角色"),
    
    // ========== 权限管理 ==========
    /** 权限管理 */
    ADMIN_PERMISSION_MANAGE("admin:permission:manage", "权限管理"),
    
    /** 查看权限 */
    ADMIN_PERMISSION_READ("admin:permission:read", "查看权限"),
    
    /** 创建权限 */
    ADMIN_PERMISSION_CREATE("admin:permission:create", "创建权限"),
    
    /** 修改权限 */
    ADMIN_PERMISSION_UPDATE("admin:permission:update", "修改权限"),
    
    /** 删除权限 */
    ADMIN_PERMISSION_DELETE("admin:permission:delete", "删除权限"),
    
    // ========== 字典管理 ==========
    /** 字典管理 */
    ADMIN_DICT_MANAGE("admin:dict:manage", "字典管理"),
    
    /** 查看字典 */
    ADMIN_DICT_READ("admin:dict:read", "查看字典"),
    
    /** 创建字典 */
    ADMIN_DICT_CREATE("admin:dict:create", "创建字典"),
    
    /** 修改字典 */
    ADMIN_DICT_UPDATE("admin:dict:update", "修改字典"),
    
    /** 删除字典 */
    ADMIN_DICT_DELETE("admin:dict:delete", "删除字典"),
    
    // ========== 系统管理 ==========
    /** 系统配置 */
    ADMIN_SYSTEM_CONFIG("admin:system:config", "系统配置"),
    
    /** 查看系统日志 */
    ADMIN_SYSTEM_LOG_READ("admin:system:log:read", "查看系统日志");
    
    private final String code;
    private final String description;
    
    AdminPermissionEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    /**
     * 根据权限编码获取枚举
     * 
     * @param code 权限编码
     * @return 对应的枚举，如果不存在则返回null
     */
    public static AdminPermissionEnum getByCode(String code) {
        for (AdminPermissionEnum permission : values()) {
            if (permission.getCode().equals(code)) {
                return permission;
            }
        }
        return null;
    }
    
    /**
     * 检查权限编码是否存在
     * 
     * @param code 权限编码
     * @return 是否存在
     */
    public static boolean contains(String code) {
        return getByCode(code) != null;
    }
    
    /**
     * 获取所有权限编码
     * 
     * @return 权限编码数组
     */
    public static String[] getAllCodes() {
        AdminPermissionEnum[] values = values();
        String[] codes = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            codes[i] = values[i].getCode();
        }
        return codes;
    }
    
    @Override
    public String toString() {
        return this.code + " - " + this.description;
    }
}
