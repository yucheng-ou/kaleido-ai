package com.xiaoo.kaleido.user.trigger.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 更新用户信息请求
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
@Schema(description = "更新用户信息请求")
public class UpdateUserRequest {

    @Schema(description = "昵称（可选）", example = "新昵称")
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickName;

    @Schema(description = "头像URL（可选）", example = "https://example.com/avatar.jpg")
    @Size(max = 255, message = "头像URL长度不能超过255个字符")
    private String avatarUrl;

    @Schema(description = "扩展信息（JSON格式，可选）", example = "{\"gender\":\"male\",\"age\":25}")
    @Size(max = 2000, message = "扩展信息长度不能超过2000个字符")
    private String extraInfo;
}
