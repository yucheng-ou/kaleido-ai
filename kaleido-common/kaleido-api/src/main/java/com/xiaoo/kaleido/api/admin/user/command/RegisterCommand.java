package com.xiaoo.kaleido.api.admin.user.command;

import com.xiaoo.kaleido.base.command.BaseCommand;

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
public class RegisterCommand extends BaseCommand {
    
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
    
    /**
     * 昵称（可选）
     */
    @Size(min = 2, max = 20, message = "昵称长度必须在2-20位之间")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9]+$", message = "昵称只能包含中文、英文和数字")
    private String nickName;
}
