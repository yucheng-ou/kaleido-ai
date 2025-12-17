package com.xiaoo.kaleido.notice.domain.model.aggregate;

import com.xiaoo.kaleido.notice.domain.constant.NoticeStatus;
import com.xiaoo.kaleido.notice.domain.constant.NoticeType;
import com.xiaoo.kaleido.notice.domain.model.valobj.NoticeContent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 通知记录聚合根
 * <p>
 * 核心业务实体，代表一次完整的通知发送记录
 * 负责维护通知的生命周期状态
 * </p>
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Data
@Builder
public class NoticeRecord {
    /**
     * 通知记录ID
     */
    private final String id;

    /**
     * 通知类型
     */
    private final NoticeType noticeType;

    /**
     * 通知状态
     */
    private NoticeStatus status;

    /**
     * 通知内容
     */
    private final NoticeContent content;

    /**
     * 接收目标（手机号、邮箱等）
     */
    private final String target;

    /**
     * 业务类型
     */
    private final String businessType;

    /**
     * 发送结果信息
     */
    private String resultMessage;

    /**
     * 第三方响应ID
     */
    private String thirdPartyId;

    /**
     * 创建时间
     */
    private final LocalDateTime createdAt;

    /**
     * 发送时间
     */
    private LocalDateTime sentAt;

    /**
     * 重试次数
     */
    private int retryCount;

    /**
     * 下次重试时间
     */
    private LocalDateTime nextRetryAt;

    /**
     * 创建通知记录聚合根
     *
     * @param id           通知记录ID
     * @param noticeType   通知类型
     * @param status       通知状态
     * @param content      通知内容
     * @param target       接收目标
     * @param businessType 业务类型
     * @param resultMessage 发送结果信息
     * @param thirdPartyId 第三方响应ID
     * @param createdAt    创建时间
     * @param sentAt       发送时间
     * @param retryCount   重试次数
     * @param nextRetryAt  下次重试时间
     */
    public NoticeRecord(String id, NoticeType noticeType, NoticeStatus status, 
                       NoticeContent content, String target, String businessType,
                       String resultMessage, String thirdPartyId, LocalDateTime createdAt,
                       LocalDateTime sentAt, int retryCount, LocalDateTime nextRetryAt) {
        this.id = id;
        this.noticeType = noticeType;
        this.status = status;
        this.content = content;
        this.target = target;
        this.businessType = businessType;
        this.resultMessage = resultMessage;
        this.thirdPartyId = thirdPartyId;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.retryCount = retryCount;
        this.nextRetryAt = nextRetryAt;
    }

    /**
     * 标记为发送中
     */
    public void markAsSending() {
        this.status = NoticeStatus.SENDING;
        this.sentAt = LocalDateTime.now();
    }

    /**
     * 标记为发送成功
     *
     * @param thirdPartyId 第三方响应ID
     * @param resultMessage 结果信息
     */
    public void markAsSuccess(String thirdPartyId, String resultMessage) {
        this.status = NoticeStatus.SUCCESS;
        this.thirdPartyId = thirdPartyId;
        this.resultMessage = resultMessage;
        this.sentAt = LocalDateTime.now();
    }

    /**
     * 标记为发送失败
     *
     * @param resultMessage 失败原因
     */
    public void markAsFailed(String resultMessage) {
        this.status = NoticeStatus.FAILED;
        this.resultMessage = resultMessage;
        this.sentAt = LocalDateTime.now();
    }

    /**
     * 标记为重试中
     *
     * @param nextRetryAt 下次重试时间
     */
    public void markAsRetrying(LocalDateTime nextRetryAt) {
        this.status = NoticeStatus.RETRYING;
        this.retryCount++;
        this.nextRetryAt = nextRetryAt;
    }

    /**
     * 是否可以重试
     *
     * @param maxRetryCount 最大重试次数
     * @return true 表示可以重试
     */
    public boolean canRetry(int maxRetryCount) {
        return this.status == NoticeStatus.FAILED 
                && this.retryCount < maxRetryCount
                && (this.nextRetryAt == null || LocalDateTime.now().isAfter(this.nextRetryAt));
    }

    /**
     * 创建新的通知记录
     *
     * @param id           通知记录ID
     * @param noticeType   通知类型
     * @param content      通知内容
     * @param target       接收目标
     * @param businessType 业务类型
     * @return 新的通知记录聚合根
     */
    public static NoticeRecord create(String id, NoticeType noticeType, NoticeContent content, 
                                     String target, String businessType) {
        return NoticeRecord.builder()
                .id(id)
                .noticeType(noticeType)
                .status(NoticeStatus.PENDING)
                .content(content)
                .target(target)
                .businessType(businessType)
                .resultMessage(null)
                .thirdPartyId(null)
                .createdAt(LocalDateTime.now())
                .sentAt(null)
                .retryCount(0)
                .nextRetryAt(null)
                .build();
    }
}
