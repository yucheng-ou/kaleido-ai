package com.xiaoo.kaleido.api.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * 用户查询请求参数
 * 支持根据ID、手机号、邀请码、昵称进行组合查询
 * 
 * @author ouyucheng
 * @date 2025/11/20
 */
@Data
@Schema(description = "用户查询请求参数")
public class UserQueryRequest {

    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "用户手机号", example = "13800138000")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String telephone;

    @Schema(description = "用户邀请码", example = "ABC123")
    private String inviteCode;

    @Schema(description = "用户昵称", example = "张三")
    private String nickName;
}
