package com.xiaoo.kaleido.api.admin.user.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

/**
 * 注册响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class RegisterResponse extends BaseResp {
    
    /**
     * 用户ID
     */
    private String userId;
}
