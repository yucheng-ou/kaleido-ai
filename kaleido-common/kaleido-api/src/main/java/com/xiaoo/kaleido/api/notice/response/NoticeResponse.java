package com.xiaoo.kaleido.api.notice.response;
import com.xiaoo.kaleido.api.notice.enums.BusinessTypeEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
/**
 * 閫氱煡鍝嶅簲瀵硅薄
 *
 * @author ouyucheng
 * @date 2025/12/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponse {
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
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新时间
     */
    private Date updatedAt;
}
