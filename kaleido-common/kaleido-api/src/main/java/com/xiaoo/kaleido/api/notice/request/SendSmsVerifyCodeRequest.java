package com.xiaoo.kaleido.api.notice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * @author ouyucheng
 * @date 2025/12/19
 * @description
 */
@Getter
public class SendSmsVerifyCodeRequest {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

}
