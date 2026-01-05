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
 * 用户注册命令
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户注册命令")
public class RegisterCommand {
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String telephone;
    
    /**
     * 密码
     */
    @Schema(description = "密码", example = "Password123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "密码必须包含字母和数字")
    private String password;
    
    /**
     * 短信验证码
     */
    @Schema(description = "短信验证码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 8, message = "验证码长度必须在4-8位之间")
    private String verificationCode;
    
    /**
     * 邀请码（可选）
     */
    @Schema(description = "邀请码", example = "INVITE123", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(min = 6, max = 20, message = "邀请码长度必须在6-20位之间")
    private String inviterCode;
    
    /**
     * 昵称（可选）
     */
    @Schema(description = "昵称", example = "张三", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(min = 2, max = 20, message = "昵称长度必须在2-20位之间")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9]+$", message = "昵称只能包含中文、英文和数字")
    private String nickName;
}
