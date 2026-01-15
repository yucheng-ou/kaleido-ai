package com.xiaoo.kaleido.notice.domain.model.aggregate;

import cn.hutool.core.util.RandomUtil;
import com.xiaoo.kaleido.base.model.entity.BaseEntity;
import com.xiaoo.kaleido.distribute.util.SnowflakeUtil;
import com.xiaoo.kaleido.notice.domain.model.valobj.RetryStatus;
import com.xiaoo.kaleido.notice.domain.model.valobj.TargetAddress;
import com.xiaoo.kaleido.api.notice.enums.BusinessTypeEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.api.notice.enums.NoticeTypeEnum;
import com.xiaoo.kaleido.notice.types.exception.NoticeErrorCode;
import com.xiaoo.kaleido.notice.types.exception.NoticeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 通知聚合根
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NoticeAggregate extends BaseEntity {

    private static final Integer VERIFY_CODE_LENGTH = 6;

    public static final Integer MAX_RETRY_NUM = 3;

    /**
     * 通知类型
     */
    private NoticeTypeEnum noticeType;

    /**
     * 目标地址（值对象）
     */
    private TargetAddress target;

    /**
     * 通知状态
     */
    private NoticeStatusEnum status;

    /**
     * 业务类型 短信验证码等
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
     * 重试状态
     */
    private RetryStatus retryStatus;


    /**
     * 创建通知聚合根（使用外部ID）
     *
     * @param noticeId     通知ID（外部传入）
     * @param noticeType   通知类型
     * @param target       目标地址
     * @param businessType 业务类型
     * @param content      通知内容
     * @return 通知聚合根
     */
    public static NoticeAggregate create(String noticeId, NoticeTypeEnum noticeType, TargetAddress target, BusinessTypeEnum businessType, String content) {

        return NoticeAggregate.builder().id(noticeId).noticeType(noticeType).target(target).status(NoticeStatusEnum.PENDING).businessType(businessType).content(content).retryStatus(RetryStatus.init()).build();
    }

    /**
     * 创建通知聚合根（自动生成ID）
     *
     * @param noticeType   通知类型
     * @param target       目标地址
     * @param businessType 业务类型
     * @param content      通知内容
     * @return 通知聚合根
     */
    public static NoticeAggregate create(NoticeTypeEnum noticeType, TargetAddress target, BusinessTypeEnum businessType, String content) {
        // 使用雪花算法生成ID
        String noticeId = SnowflakeUtil.newSnowflakeId();
        return create(noticeId, noticeType, target, businessType, content);
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
        this.resultMessage = resultMessage;
        this.sentAt = new Date();

        retryStatus.update(MAX_RETRY_NUM);

        if (isNeedRetry()) {
            //需要进行重试 标记为待发送
            this.status = NoticeStatusEnum.PENDING;
        } else {
            //直接记录为发送失败
            this.status = NoticeStatusEnum.FAILED;
        }
    }

    /**
     * 验证通知数据
     */
    public void validate() {
        if (noticeType == null) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_TYPE_EMPTY);
        }
        if (target == null) {
            throw NoticeException.of(NoticeErrorCode.TARGET_USER_EMPTY);
        }
        if (businessType == null) {
            throw NoticeException.of(NoticeErrorCode.BUSINESS_TYPE_EMPTY);
        }
        if (content == null) {
            throw NoticeException.of(NoticeErrorCode.NOTICE_CONTENT_EMPTY);
        }
    }

    public static String generateVerifyCode() {
        return RandomUtil.randomNumbers(VERIFY_CODE_LENGTH);
    }

    /**
     * 获取当前重试次数
     */
    public int getCurrentRetryCount() {
        return retryStatus != null ? retryStatus.getRetryNum() : 0;
    }

    /**
     * 获取下次重试时间
     */
    public Date getNextRetryAt() {
        return retryStatus != null ? retryStatus.getNextRetryAt() : null;
    }

    /**
     * 获取目标地址字符串
     */
    public String getTargetAddress() {
        return target != null ? target.getFormattedAddress() : null;
    }

    public boolean isNeedRetry() {
        return retryStatus.getRetryNum() < MAX_RETRY_NUM;
    }

}
