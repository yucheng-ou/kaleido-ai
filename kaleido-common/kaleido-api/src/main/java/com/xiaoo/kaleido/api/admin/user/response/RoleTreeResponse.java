package com.xiaoo.kaleido.api.admin.user.response;

import lombok.Data;

import java.util.List;

/**
 * 角色树响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
public class RoleTreeResponse {
    
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
     * 子角色列表
     */
    private List<RoleTreeResponse> children;
}
