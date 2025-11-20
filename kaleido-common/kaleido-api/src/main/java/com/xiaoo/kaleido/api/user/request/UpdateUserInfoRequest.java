package com.xiaoo.kaleido.api.user.request;

import com.xiaoo.kaleido.base.request.BaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新用户基本信息请求参数
 *
 * @author ouyucheng
 * @date 2025/11/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "更新用户基本信息请求参数")
public class UpdateUserInfoRequest extends BaseReq {

    @Schema(description = "用户ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "用户昵称", example = "新昵称")
    private String nickName;

    @Schema(description = "用户头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    @Schema(description = "用户手机号", example = "13800138000")
    private String telephone;
}
