package com.xiaoo.kaleido.api.admin.auth.command;

import com.xiaoo.kaleido.api.admin.auth.enums.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新权限命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新权限命令")
public class UpdatePermissionCommand {
    
    /**
     * 权限ID
     */
    @Schema(description = "权限ID", example = "1234567890123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限ID不能为空")
    private String permissionId;
    
    /**
     * 权限名称
     */
    @Schema(description = "权限名称", example = "创建用户", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限名称不能为空")
    @Size(min = 2, max = 50, message = "权限名称长度必须在2-50位之间")
    private String name;
    
    /**
     * 权限类型
     */
    @Schema(description = "权限类型", example = "MENU", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "权限类型不能为空")
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
    @Size(max = 100, message = "图标长度不能超过100位")
    private String icon;
    
    /**
     * 前端路由路径
     */
    @Schema(description = "前端路由路径", example = "/user")
    @Size(max = 200, message = "前端路由路径长度不能超过200位")
    private String path;
    
    /**
     * 前端组件路径
     */
    @Schema(description = "前端组件路径", example = "views/user/index")
    @Size(max = 200, message = "前端组件路径长度不能超过200位")
    private String component;
    
    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏", example = "false")
    private Boolean isHidden;
}
