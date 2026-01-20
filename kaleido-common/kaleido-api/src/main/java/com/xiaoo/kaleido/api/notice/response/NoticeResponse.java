package com.xiaoo.kaleido.api.notice.response;
import com.xiaoo.kaleido.api.notice.enums.BusinessTypeEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.base.response.BaseResp;
import lombok.*;

import java.util.Date;
/**
 * 通知响应对象
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponse extends BaseResp {
    /**
     * 通知ID
     */
    private String id;
    /**
     * 通知类型
     */
    private NoticeTypeEnum noticeType;
    /**
     * 目标地址
     */
    private String targetAddress;
    /**
     * 通知状态
     */
    private NoticeStatusEnum status;
    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType;
    /**
     * 通知内容
     */
    private String content;
    /**
     * 发送结果信息
     */
    private String resultMessage;
    /**
     * 发送时间
     */
    private Date sentAt;

}
