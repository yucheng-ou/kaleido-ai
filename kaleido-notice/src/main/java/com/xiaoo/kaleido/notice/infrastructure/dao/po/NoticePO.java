package com.xiaoo.kaleido.notice.infrastructure.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaoo.kaleido.ds.po.BasePO;
import com.xiaoo.kaleido.notice.types.enums.BusinessTypeEnum;
import com.xiaoo.kaleido.notice.types.enums.NoticeStatusEnum;
import com.xiaoo.kaleido.notice.types.enums.NoticeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 通知持久化对象
 *
 * @author ouyucheng
 * @date 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("notice")
public class NoticePO extends BasePO {

    /**
     * 通知类型
     */
    @TableField("notice_type")
    private NoticeTypeEnum noticeType;

    /**
     * 目标地址
     */
    @TableField("target_address")
    private String targetAddress;

    /**
     * 通知状态
     */
    @TableField("status")
    private NoticeStatusEnum status;

    /**
     * 业务类型
     */
    @TableField("business_type")
    private BusinessTypeEnum businessType;

    /**
     * 通知内容
     */
    @TableField("content")
    private String content;

    /**
     * 发送结果信息
     */
    @TableField("result_message")
    private String resultMessage;

    /**
     * 发送时间
     */
    @TableField("sent_at")
    private Date sentAt;

    /**
     * 当前重试次数
     */
    @TableField("retry_num")
    private Integer retryNum;

    /**
     * 下次重试时间
     */
    @TableField("next_retry_at")
    private Date nextRetryAt;

    /**
     * 最大重试次数
     */
    @TableField("max_retry_num")
    private Integer maxRetryNum;
}
