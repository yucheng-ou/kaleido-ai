package com.xiaoo.kaleido.notice.command;

import lombok.Data;

/**
 * 生成验证码命令
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Data
public class GenerateVerificationCodeCommand {
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 业务类型
     */
    private String businessType;
}
