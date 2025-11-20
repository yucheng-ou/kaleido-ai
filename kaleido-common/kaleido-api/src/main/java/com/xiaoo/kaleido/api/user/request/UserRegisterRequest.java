package com.xiaoo.kaleido.api.user.request;

import com.xiaoo.kaleido.base.request.BaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户注册请求参数")
public class UserRegisterRequest extends BaseReq {

    @Schema(description = "手机号码", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String telephone;

    @Schema(description = "邀请码", example = "INVITE123")
    private String inviteCode;

    @Schema(description = "验证码", example = "8888")
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
