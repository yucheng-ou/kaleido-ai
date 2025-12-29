package com.xiaoo.kaleido.api.notice.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送短信验证码命令
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "发送短信验证码命令")
public class SendSmsVerifyCodeCommand {
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
}
