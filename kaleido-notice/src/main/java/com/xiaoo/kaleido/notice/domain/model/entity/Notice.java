package com.xiaoo.kaleido.sms.domain.model.entity;

import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.sms.types.enums.BusinessTypeEnum;
import com.xiaoo.kaleido.sms.types.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.sms.types.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.sms.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.sms.types.exception.NoticeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 通知实体
 *
 * @author ouyucheng
 * @date 2025/12/18
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseEntity {

    /**
     * 通知类型
     */
    private NoticeTypeEnum noticeTypeEnum;

    /**
     * 接收目标（手机号、邮箱等）
     */
    private String target;

    /**
     * 通知状态
     */
    private NoticeStatusEnum status;

    /**
     * 业务类型 短信验证码等
     */
    private BusinessTypeEnum businessType;

    /**
     * 发送结果信息
     */
    private String resultMessage;


    /**
     * 发送时间
     */
    private Date sentAt;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 下次重试时间
     */
    private Date nextRetryAt;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 创建通知实体
     *
     * @param noticeTypeEnum   通知类型
     * @param target       接收目标
     * @param businessType 业务类型
     * @param title        消息标题
     * @param content      消息内容
     * @param templateId   模板ID
     * @return 通知实体
     */
    public static Notice create(NoticeTypeEnum noticeTypeEnum, String target, BusinessTypeEnum businessType,
                                String title, String content, String templateId) {
        return Notice.builder()
                .id(SnowflakeUtil.newSnowflakeId())
                .noticeTypeEnum(noticeTypeEnum)
                .status(NoticeStatusEnum.PENDING)
                .target(target)
                .businessType(businessType)
                .content(content)
                .templateId(templateId)
                .retryCount(0)
                .build();
    }

    /**
     * 创建通知实体（无模板）
     *
     * @param noticeTypeEnum   通知类型
     * @param target       接收目标
     * @param businessType 业务类型
     * @param title        消息标题
     * @param content      消息内容
     * @return 通知实体
     */
    public static Notice createWithoutTemplate(NoticeTypeEnum noticeTypeEnum, String target, BusinessTypeEnum businessType,
                                               String title, String content) {
        return create(noticeTypeEnum, target, businessType, title, content, null);
    }


    /**
     * 标记为发送成功
     *
     * @param resultMessage 结果信息
     */
    public void markAsSuccess(String resultMessage) {
        this.status = NoticeStatusEnum.SUCCESS;
        this.resultMessage = resultMessage;
        this.sentAt = new Date();
    }

    /**
     * 标记为发送失败
     *
     * @param resultMessage 失败原因
     */
    public void markAsFailed(String resultMessage) {
        this.status = NoticeStatusEnum.FAILED;
        this.resultMessage = resultMessage;
        this.sentAt = new Date();
    }

    /**
     * 是否可以重试
     *
     * @param maxRetryCount 最大重试次数
     * @return true 表示可以重试
     */
    public boolean canRetry(int maxRetryCount) {
        //已经标记为发送失败或发送失败 不可以重试
        if (this.status != NoticeStatusEnum.PENDING) {
            return false;
        }

        //重试次数为空或者大于最大重试次数 不能重试
        if (this.retryCount == null || this.retryCount >= maxRetryCount) {
            return false;
        }

        //下次重试时间为空 可以重试
        if (this.nextRetryAt == null) {
            return true;
        }

        //当前时间晚于下次重试时间为空 可以重试
        return new Date().after(this.nextRetryAt);
    }

    /**
     * 验证通知数据
     */
    public void validate() {
        if (noticeTypeEnum == null) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TYPE_EMPTY);
        }
        if (target == null || target.trim().isEmpty()) {
            throw NoticeException.of(NoticeErrorCode.TARGET_USER_EMPTY);
        }
        if (businessType == null) {
            throw NoticeException.of(NoticeErrorCode.BUSINESS_TYPE_EMPTY);
        }
    }
}
