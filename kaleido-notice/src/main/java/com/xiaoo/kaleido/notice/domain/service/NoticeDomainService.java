package com.xiaoo.kaleido.notice.domain.service;

import com.xiaoo.kaleido.notice.domain.constant.NoticeType;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeRecord;
import com.xiaoo.kaleido.notice.domain.model.valobj.NoticeContent;
import com.xiaoo.kaleido.notice.domain.model.valobj.VerificationCode;

/**
 * 通知领域服务接口
 * <p>
 * 处理通知发送的核心业务逻辑
 * 支持模板渲染、验证码生成、发送策略等
 * </p>
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public interface NoticeDomainService {

    /**
     * 发送通知
     *
     * @param noticeType   通知类型
     * @param target       接收目标（手机号、邮箱等）
     * @param content      通知内容
     * @param businessType 业务类型
     * @return 通知记录ID
     */
    String sendNotice(NoticeType noticeType, String target, NoticeContent content, String businessType);

    /**
     * 生成并发送短信验证码
     *
     * @param mobile       手机号
     * @param businessType 业务类型
     * @return 验证码值
     */
    String generateAndSendSmsVerificationCode(String mobile, String businessType);

    /**
     * 验证短信验证码
     *
     * @param mobile       手机号
     * @param code         验证码
     * @param businessType 业务类型
     * @return 是否验证成功
     */
    boolean verifySmsCode(String mobile, String code, String businessType);

    /**
     * 根据ID获取通知记录
     *
     * @param noticeId 通知记录ID
     * @return 通知记录聚合根
     */
    NoticeRecord getNoticeRecordById(String noticeId);

    /**
     * 重试发送失败的通知
     *
     * @param noticeId 通知记录ID
     * @return 是否重试成功
     */
    boolean retryFailedNotice(String noticeId);

    /**
     * 渲染通知模板
     *
     * @param templateId 模板ID
     * @param variables  模板变量
     * @return 渲染后的通知内容
     */
    NoticeContent renderNoticeTemplate(String templateId, java.util.Map<String, Object> variables);

    /**
     * 生成验证码
     *
     * @param mobile       手机号
     * @param businessType 业务类型
     * @param length       验证码长度
     * @param expireMinutes 过期时间（分钟）
     * @return 验证码值对象
     */
    VerificationCode generateVerificationCode(String mobile, String businessType, int length, int expireMinutes);
}
