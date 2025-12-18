package com.xiaoo.kaleido.sms.query.dto;

import com.xiaoo.kaleido.sms.types.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.sms.types.enums.NoticeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 通知记录DTO
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRecordDTO {
    /**
     * 通知记录ID
     */
    private String id;

    /**
     * 通知类型
     */
    private NoticeTypeEnum noticeTypeEnum;

    /**
     * 接收目标（手机号、邮箱等）
     */
    private String target;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知状态
     */
    private NoticeStatusEnum status;

    /**
     * 发送结果消息
     */
    private String resultMessage;

    /**
     * 第三方服务ID
     */
    private String thirdPartyId;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 从聚合根转换为DTO
     *
     * @param noticeRecord 通知记录聚合根
     * @return 通知记录DTO
     */
    public static NoticeRecordDTO from(NoticeRecord noticeRecord) {
        if (noticeRecord == null) {
            return null;
        }

        return NoticeRecordDTO.builder()
                .id(noticeRecord.getId())
                .noticeTypeEnum(noticeRecord.getNotice().getNoticeTypeEnum())
                .target(noticeRecord.getNotice().getTarget())
                .businessType(noticeRecord.getNotice().getBusinessType())
                .content(noticeRecord.getNotice().getContent())
                .status(noticeRecord.getStatus())
                .resultMessage(noticeRecord.getResultMessage())
                .thirdPartyId(noticeRecord.getThirdPartyId())
                .retryCount(noticeRecord.getRetryCount())
                .createdAt(noticeRecord.getNotice().getCreatedAt())
                .updatedAt(noticeRecord.getNotice().getUpdatedAt())
                .build();
    }
}
