package com.xiaoo.kaleido.api.admin.user.response;

import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.Date;

/**
 * 短信验证码响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class SmsCodeResponse extends BaseResp {
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 验证码（测试环境返回）
     */
    private String code;
    
    /**
     * 发送时间
     */
    private Date sendTime;
}
