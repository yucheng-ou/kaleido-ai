package com.xiaoo.kaleido.api.user.command;
import com.xiaoo.kaleido.base.command.BaseCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 娣诲姞鐢ㄦ埛鍛戒护
 *
 * @author ouyucheng
 * @date 2025/12/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserCommand extends BaseCommand {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String telephone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "密码必须包含字母和数字")
    private String password;

    /**
     * 邀请码（可选）
     */
    @Size(min = 6, max = 20, message = "邀请码长度必须在6-20位之间")
    private String inviterCode;

    /**
     * 閭€璇风爜锛堝彲閫夛級
     */
    @Size(min = 0, max = 255)
    @Pattern(regexp = ".*")
    private String nickName;
}
