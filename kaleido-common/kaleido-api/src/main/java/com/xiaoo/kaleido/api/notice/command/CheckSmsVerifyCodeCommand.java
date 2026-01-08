package com.xiaoo.kaleido.api.notice.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 鏍￠獙鐭俊楠岃瘉鐮佸懡浠?
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckSmsVerifyCodeCommand extends BaseCommand {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 8, message = "验证码长度必须在4-8位之间")
    private String verifyCode;
}
