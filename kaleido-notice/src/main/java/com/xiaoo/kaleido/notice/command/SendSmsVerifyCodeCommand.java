package com.xiaoo.kaleido.notice.command;

import lombok.Builder;
import lombok.Data;


/**
 * 发送通知命令
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
@Data
@Builder
public class SendSmsVerifyCodeCommand {

    /**
     * 目标手机号
     */
    private String mobile;

}
