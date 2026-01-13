package com.xiaoo.kaleido.api.user.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 注册用户命令
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class RegisterUserCommand extends BaseCommand {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String telephone;

    /**
     * 短信验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 8, message = "验证码长度必须在4-8位之间")
    private String verificationCode;

    /**
     * 邀请码（可选）
     */
    @Size(min = 6, max = 20, message = "邀请码长度必须在6-20位之间")
    private String inviterCode;

}
