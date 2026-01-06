package com.xiaoo.kaleido.api.admin.auth.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分配权限给角色命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分配权限给角色命令")
public class AssignPermissionsToRoleCommand extends BaseCommand {
    
    /**
     * 角色ID
     */
    @Schema(description = "角色ID", example = "1234567890123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色ID不能为空")
    private String roleId;
    
    /**
     * 权限ID列表
     */
    @Schema(description = "权限ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "权限ID列表不能为空")
    private List<String> permissionIds;
}
