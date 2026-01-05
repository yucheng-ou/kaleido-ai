package com.xiaoo.kaleido.api.admin.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 角色信息响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Schema(description = "角色信息响应")
public class RoleInfoResponse {
    
    /**
     * 角色ID
     */
    @Schema(description = "角色ID", example = "1234567890123456789")
    private String roleId;
    
    /**
     * 角色编码
     */
    @Schema(description = "角色编码", example = "admin")
    private String code;
    
    /**
     * 角色名称
     */
    @Schema(description = "角色名称", example = "管理员")
    private String name;
    
    /**
     * 角色描述
     */
    @Schema(description = "角色描述", example = "系统管理员角色")
    private String description;
    
    /**
     * 是否系统角色
     */
    @Schema(description = "是否系统角色", example = "false")
    private Boolean isSystem;
    
    /**
     * 是否启用
     */
    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;

    /**
     * 权限ID列表
     */
    @Schema(description = "权限ID列表")
    private List<String> permissionIds;
    
    /**
     * 权限信息列表
     */
    @Schema(description = "权限信息列表")
    private List<PermissionInfoResponse> permissions;
}
