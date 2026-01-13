package com.xiaoo.kaleido.api.user.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

/**
 * 登录响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class UserLoginResponse extends BaseResp {
    /**
     * 用户ID
     */
    String userId;
    /**
     * 认证令牌
     */
    String token;
    /**
     * 用户信息
     */
    UserInfoResponse userInfo;
}
