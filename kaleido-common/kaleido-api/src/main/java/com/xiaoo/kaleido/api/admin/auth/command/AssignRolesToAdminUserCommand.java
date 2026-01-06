package com.xiaoo.kaleido.api.admin.auth.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分配角色给管理员命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分配角色给管理员命令")
public class AssignRolesToAdminUserCommand extends BaseCommand {
    
    /**
     * 管理员ID
     */
    @Schema(description = "管理员ID", example = "1234567890123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "管理员ID不能为空")
    private String adminUserId;
    
    /**
     * 角色ID列表
     */
    @Schema(description = "角色ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "角色ID列表不能为空")
    private List<String> roleIds;
}
