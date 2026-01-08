package com.xiaoo.kaleido.api.admin.user.response;

import lombok.Data;

/**
 * 登录响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
public class AdminLoginResponse {
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 认证令牌
     */
    private String token;
    
    /**
     * 用户信息
     */
    private AdminUserInfoResponse userInfo;
}
