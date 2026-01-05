package com.xiaoo.kaleido.api.admin.auth.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新管理员信息命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新管理员信息命令")
public class UpdateAdminUserCommand {
    
    /**
     * 管理员ID
     */
    @Schema(description = "管理员ID", example = "1234567890123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "管理员ID不能为空")
    private String adminUserId;
    
    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名", example = "张三")
    @Size(min = 2, max = 20, message = "真实姓名长度必须在2-20位之间")
    private String realName;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
}
