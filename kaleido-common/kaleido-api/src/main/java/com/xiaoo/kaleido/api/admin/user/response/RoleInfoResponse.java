package com.xiaoo.kaleido.api.admin.user.response;

import lombok.Data;

import java.util.List;

/**
 * 角色信息响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
public class RoleInfoResponse {
    
    /**
     * 角色ID
     */
    private String roleId;
    
    /**
     * 角色编码
     */
    private String code;
    
    /**
     * 角色名称
     */
    private String name;
    
    /**
     * 角色描述
     */
    private String description;
    
    /**
     * 是否系统角色
     */
    private Boolean isSystem;
    
    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 权限ID列表
     */
    private List<String> permissionIds;
    
    /**
     * 权限信息列表
     */
    private List<PermissionInfoResponse> permissions;
}
