package com.xiaoo.kaleido.api.admin.user.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import com.xiaoo.kaleido.api.admin.user.enums.PermissionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 添加权限命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class AddPermissionCommand extends BaseCommand {
    
    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    @Size(min = 2, max = 50, message = "权限编码长度必须在2-50位之间")
    @Pattern(regexp = "^[a-zA-Z0-9:_-]+$", message = "权限编码只能包含字母、数字、冒号、下划线和短横线")
    private String code;
    
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
