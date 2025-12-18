package com.xiaoo.kaleido.sms.command;

import com.xiaoo.kaleido.sms.types.enums.NoticeTypeEnum;
import lombok.Data;

/**
 * 发送通知命令
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Data
public class SendNoticeCommand {
    /**
     * 通知类型
     */
    private NoticeTypeEnum noticeTypeEnum;

    /**
     * 接收目标（手机号、邮箱等）
     */
    private String target;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 业务类型
     */
    private String businessType;
}
