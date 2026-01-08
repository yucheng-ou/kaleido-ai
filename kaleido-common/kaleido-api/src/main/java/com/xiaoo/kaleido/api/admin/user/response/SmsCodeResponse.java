package com.xiaoo.kaleido.api.admin.user.response;

import lombok.Data;

import java.util.Date;

/**
 * 短信验证码响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
public class SmsCodeResponse {
    
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
