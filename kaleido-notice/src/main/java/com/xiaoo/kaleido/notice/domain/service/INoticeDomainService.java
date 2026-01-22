package com.xiaoo.kaleido.notice.domain.service;

import com.xiaoo.kaleido.api.notice.enums.TargetTypeEnum;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeAggregate;
import com.xiaoo.kaleido.notice.domain.model.aggregate.NoticeTemplateAggregate;

/**
 * 通知领域服务接口
 *
 * @author ouyucheng
 * @date 2025/12/19
 */
public interface INoticeDomainService {

    /**
     * 创建短信验证码通知聚合根

     * 根据手机号和内容创建短信验证码通知，默认目标类型为普通用户
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @return 通知聚合根
     */
    NoticeAggregate createSmsVerifyCodeAggregate(String mobile, String content);

    /**
     * 创建短信验证码通知聚合根

     * 根据手机号、内容和目标类型创建短信验证码通知
     *
     * @param mobile     手机号
     * @param content    短信内容
     * @param targetType 目标类型（用户/管理员等）
     * @return 通知聚合根
     */
    NoticeAggregate createSmsVerifyCodeAggregate(String mobile, String content, TargetTypeEnum targetType);

    /**
     * 生成验证码
     *
     * @return 6位数字验证码
     */
    String generateVerifyCode();

    /**
     * 渲染模板内容
     *
     * @param template 模板聚合根
     * @param verifyCode 验证码
     * @return 渲染后的内容
     */
    String renderTemplate(NoticeTemplateAggregate template, String verifyCode);

    /**
     * 处理手机号参数
     *
     * @param mobile 原始手机号
     * @return 处理后的手机号（去除空格）
     */
    String processMobile(String mobile);

}
