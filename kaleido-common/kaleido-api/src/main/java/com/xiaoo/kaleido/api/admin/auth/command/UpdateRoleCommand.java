package com.xiaoo.kaleido.api.admin.auth.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新角色命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新角色命令")
public class UpdateRoleCommand {
    
    /**
     * 角色ID
     */
    @Schema(description = "角色ID", example = "1234567890123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色ID不能为空")
    private String roleId;
    
    /**
     * 角色名称
     */
    @Schema(description = "角色名称", example = "管理员", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 2, max = 50, message = "角色名称长度必须在2-50位之间")
    private String name;
    
    /**
     * 角色描述
     */
    @Schema(description = "角色描述", example = "系统管理员角色")
    @Size(max = 200, message = "角色描述长度不能超过200位")
    private String description;
}
