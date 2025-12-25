package com.xiaoo.kaleido.api.user.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建用户RPC请求
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
public class CreateUserRpcRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 邀请码（可选）
     */
    private String inviterCode;

    /**
     * 邮箱（可选）
     */
    private String email;

    /**
     * 昵称（可选）
     */
    private String nickName;
}
