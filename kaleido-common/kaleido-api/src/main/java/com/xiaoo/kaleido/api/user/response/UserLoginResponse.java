package com.xiaoo.kaleido.api.user.response;
import lombok.Data;
/**
 * 登录响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
public class UserLoginResponse {
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
    private UserInfoResponse userInfo;
}
