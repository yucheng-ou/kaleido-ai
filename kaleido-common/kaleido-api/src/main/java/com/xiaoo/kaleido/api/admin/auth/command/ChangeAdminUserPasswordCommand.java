package com.xiaoo.kaleido.api.admin.auth.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 修改管理员密码命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "修改管理员密码命令")
public class ChangeAdminUserPasswordCommand {
    
    /**
     * 管理员ID
     */
    @Schema(description = "管理员ID", example = "1234567890123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "管理员ID不能为空")
    private String adminUserId;
    
    /**
     * 旧密码
     */
    @Schema(description = "旧密码", example = "OldPassword123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    
    /**
     * 新密码
     */
    @Schema(description = "新密码", example = "NewPassword123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "新密码长度必须在6-20位之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "新密码必须包含字母和数字")
    private String newPassword;
}
