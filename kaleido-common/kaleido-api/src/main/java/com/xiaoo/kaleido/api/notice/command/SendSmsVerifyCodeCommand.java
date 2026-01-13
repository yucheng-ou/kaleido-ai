package com.xiaoo.kaleido.api.notice.command;
import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * 发送短信验证码command
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class SendSmsVerifyCodeCommand extends BaseCommand {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    /**
     * 目标类型
     */
    @NotNull(message = "推送目标类型不能为空")
    private TargetTypeEnum targetType;
}
