package com.xiaoo.kaleido.api.user.response;

import com.xiaoo.kaleido.api.admin.auth.response.AdminUserInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Schema(description = "登录响应")
public class UserLoginResponse {
    
    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1234567890123456789")
    private String userId;
    
    /**
     * 认证令牌
     */
    @Schema(description = "认证令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private UserInfoResponse userInfo;
}
