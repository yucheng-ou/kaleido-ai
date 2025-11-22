package com.xiaoo.kaleido.api.user.request;

import com.xiaoo.kaleido.api.user.constant.UserStatusEnum;
import com.xiaoo.kaleido.base.request.BasePageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页用户查询请求参数
 * 支持根据ID、手机号、邀请码、昵称进行组合查询，包含分页参数
 * 
 * @author ouyucheng
 * @date 2025/11/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分页用户查询请求参数")
public class PageUserQueryRequest extends BasePageReq {

    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "用户手机号", example = "13800138000")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String telephone;

    @Schema(description = "用户邀请码", example = "ABC123")
    private String inviteCode;

    @Schema(description = "用户状态 ACTIVE-活跃 FROZEN-冻结", example = "ACTIVE")
    private UserStatusEnum statusEnum;

    @Schema(description = "用户昵称", example = "张三")
    private String nickName;
}
