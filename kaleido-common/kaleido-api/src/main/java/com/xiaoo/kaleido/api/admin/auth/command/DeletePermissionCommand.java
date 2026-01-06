package com.xiaoo.kaleido.api.admin.auth.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 删除权限命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "删除权限命令")
public class DeletePermissionCommand extends BaseCommand {
    
    /**
     * 权限ID
     */
    @Schema(description = "权限ID", example = "1234567890123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "权限ID不能为空")
    private String permissionId;
}
