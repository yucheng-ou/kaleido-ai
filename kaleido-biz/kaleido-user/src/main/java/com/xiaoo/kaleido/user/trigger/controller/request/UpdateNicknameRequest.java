package com.xiaoo.kaleido.user.trigger.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 修改用户昵称请求
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
@Schema(description = "修改用户昵称请求")
public class UpdateNicknameRequest {

    @Schema(description = "新昵称", required = true, example = "新昵称")
    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 20, message = "昵称长度必须在2-20位之间")
    private String nickName;
}
