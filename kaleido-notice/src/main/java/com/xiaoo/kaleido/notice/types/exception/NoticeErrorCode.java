package com.xiaoo.kaleido.sms.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 通知服务错误码枚举
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
@Getter
public enum NoticeErrorCode implements ErrorCode {

    // 通用错误
    NOTICE_SEND_FAILED("NOTICE_SEND_FAILED", "通知发送失败"),
    NOTICE_RECORD_NOT_FOUND("NOTICE_RECORD_NOT_FOUND", "通知记录不存在"),
    NOTICE_TEMPLATE_NOT_FOUND("NOTICE_TEMPLATE_NOT_FOUND", "通知模板不存在"),
    NOTICE_TEMPLATE_DISABLED("NOTICE_TEMPLATE_DISABLED", "通知模板已禁用"),
    
    // 验证码相关错误
    VERIFICATION_CODE_GENERATE_FAILED("VERIFICATION_CODE_GENERATE_FAILED", "验证码生成失败"),
    VERIFICATION_CODE_SEND_FAILED("VERIFICATION_CODE_SEND_FAILED", "验证码发送失败"),
    VERIFICATION_CODE_INVALID("VERIFICATION_CODE_INVALID", "验证码无效"),
    VERIFICATION_CODE_EXPIRED("VERIFICATION_CODE_EXPIRED", "验证码已过期"),
    VERIFICATION_CODE_USED("VERIFICATION_CODE_USED", "验证码已使用"),
    VERIFICATION_CODE_MAX_ATTEMPTS("VERIFICATION_CODE_MAX_ATTEMPTS", "验证码验证次数超限"),
    
    // 参数验证错误
    TARGET_USER_EMPTY("TARGET_USER_EMPTY", "目标用户不能为空"),
    BUSINESS_TYPE_EMPTY("BUSINESS_TYPE_EMPTY", "业务类型不能为空"),
    NOTICE_CONTENT_EMPTY("NOTICE_CONTENT_EMPTY", "通知内容不能为空"),
    NOTICE_TARGET_EMPTY("NOTICE_TARGET_EMPTY", "通知目标不能为空"),
    NOTICE_TYPE_EMPTY("NOTICE_TYPE_EMPTY", "通知类型不能为空"),
    
    // 模板相关错误
    TEMPLATE_VARIABLE_MISMATCH("TEMPLATE_VARIABLE_MISMATCH", "模板变量不匹配"),
    TEMPLATE_RENDER_FAILED("TEMPLATE_RENDER_FAILED", "模板渲染失败"),
    
    // 发送限制错误（预留）
    SEND_FREQUENCY_LIMIT("SEND_FREQUENCY_LIMIT", "发送频率超限"),
    DAILY_SEND_LIMIT("DAILY_SEND_LIMIT", "每日发送次数超限"),
    
    // 重试相关错误
    RETRY_FAILED("RETRY_FAILED", "重试发送失败"),
    MAX_RETRY_COUNT_EXCEEDED("MAX_RETRY_COUNT_EXCEEDED", "超过最大重试次数"),
    
    // 第三方服务错误
    THIRD_PARTY_SERVICE_ERROR("THIRD_PARTY_SERVICE_ERROR", "第三方服务错误"),
    SMS_SERVICE_ERROR("SMS_SERVICE_ERROR", "短信服务错误"),
    EMAIL_SERVICE_ERROR("EMAIL_SERVICE_ERROR", "邮件服务错误");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    NoticeErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
