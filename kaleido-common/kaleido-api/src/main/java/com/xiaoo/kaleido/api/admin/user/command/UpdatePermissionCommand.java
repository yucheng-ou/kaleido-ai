package com.xiaoo.kaleido.api.admin.user.command;

import com.xiaoo.kaleido.base.command.BaseCommand;
import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;
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
public class UpdatePermissionCommand extends BaseCommand {
    /**
     * 权限ID
     */
    @NotBlank(message = "权限ID不能为空")
    private String permissionId;
    
    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Size(min = 2, max = 50, message = "权限名称长度必须在2-50位之间")
    private String name;
    
    /**
     * 权限类型
     */
    @NotNull(message = "权限类型不能为空")
    private PermissionType type;
    
    /**
     * 父权限ID
     */
    private String parentId;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 图标
     */
    @Size(max = 100, message = "图标长度不能超过100位")
    private String icon;
    
    /**
     * 前端路由路径
     */
    @Size(max = 200, message = "前端路由路径长度不能超过200位")
    private String path;
    
    /**
     * 前端组件路径
     */
    @Size(max = 200, message = "前端组件路径长度不能超过200位")
    private String component;
    
    /**
     * 是否隐藏
     */
    private Boolean isHidden;
}
