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
 * 添加管理员命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "添加管理员命令")
public class AddAdminUserCommand {
    
    /**
     * 管理员账号
     */
    @Schema(description = "管理员账号", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "管理员账号不能为空")
    @Size(min = 4, max = 20, message = "管理员账号长度必须在4-20位之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "管理员账号只能包含字母、数字和下划线")
    private String username;
    
    /**
     * 密码
     */
    @Schema(description = "密码", example = "Admin123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "密码必须包含字母和数字")
    private String password;
    
    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "真实姓名不能为空")
    @Size(min = 2, max = 20, message = "真实姓名长度必须在2-20位之间")
    private String realName;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
}
