package com.xiaoo.kaleido.api.admin.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 短信验证码响应
 *
 * @author ouyucheng
 * @date 2025/12/31
 */
@Data
@Schema(description = "短信验证码响应")
public class SmsCodeResponse {
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    private String mobile;
    
    /**
     * 验证码（测试环境返回）
     */
    @Schema(description = "验证码（测试环境返回）", example = "123456")
    private String code;
    
    /**
     * 发送时间
     */
    @Schema(description = "发送时间", example = "2025-12-31T11:13:00.000+08:00")
    private Date sendTime;
}
