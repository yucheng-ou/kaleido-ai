package com.xiaoo.kaleido.api.admin.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 注册响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Schema(description = "注册响应")
public class RegisterResponse {
    
    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1234567890123456789")
    private String userId;
}
