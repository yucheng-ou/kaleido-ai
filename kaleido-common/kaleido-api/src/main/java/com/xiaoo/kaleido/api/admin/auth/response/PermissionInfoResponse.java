package com.xiaoo.kaleido.api.admin.auth.response;

import com.xiaoo.kaleido.api.admin.auth.enums.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 权限信息响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Schema(description = "权限信息响应")
public class PermissionInfoResponse {
    
    /**
     * 权限ID
     */
    @Schema(description = "权限ID", example = "1234567890123456789")
    private String permissionId;
    
    /**
     * 权限编码
     */
    @Schema(description = "权限编码", example = "user:create")
    private String code;
    
    /**
     * 权限名称
     */
    @Schema(description = "权限名称", example = "创建用户")
    private String name;
    
    /**
     * 权限类型
     */
    @Schema(description = "权限类型", example = "MENU")
    private PermissionType type;
    
    /**
     * 父权限ID
     */
    @Schema(description = "父权限ID", example = "1234567890123456789")
    private String parentId;
    
    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    private Integer sort;
    
    /**
     * 图标
     */
    @Schema(description = "图标", example = "user")
    private String icon;
    
    /**
     * 前端路由路径
     */
    @Schema(description = "前端路由路径", example = "/user")
    private String path;
    
    /**
     * 前端组件路径
     */
    @Schema(description = "前端组件路径", example = "views/user/index")
    private String component;
    
    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏", example = "false")
    private Boolean isHidden;
    
    /**
     * 子权限列表
     */
    @Schema(description = "子权限列表")
    private List<PermissionInfoResponse> children;
}
