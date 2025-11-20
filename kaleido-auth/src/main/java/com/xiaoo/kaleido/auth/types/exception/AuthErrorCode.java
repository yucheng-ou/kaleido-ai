package com.xiaoo.kaleido.auth.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description
 */
@Getter
public enum AuthErrorCode implements ErrorCode {

    AUTH_TOKEN_INVALID("AUTH_TOKEN_INVALID", "token无效"),
    AUTH_TOKEN_EXPIRED("AUTH_TOKEN_EXPIRED", "token已过期"),
    AUTH_TOKEN_MISSING("AUTH_TOKEN_MISSING", "token缺失"),
    AUTH_PERMISSION_DENIED("AUTH_PERMISSION_DENIED", "权限不足"),
    AUTH_LOGIN_FAILED("AUTH_LOGIN_FAILED", "登录失败"),
    AUTH_ACCOUNT_LOCKED("AUTH_ACCOUNT_FROZEN", "账户已被冻结"),
    AUTH_CAPTCHA_ERROR("AUTH_CAPTCHA_ERROR", "验证码错误"),
    AUTH_REQUEST_FREQUENT("AUTH_REQUEST_FREQUENT", "请求过于频繁，请稍后重试"),
    AUTH_SESSION_EXPIRED("AUTH_SESSION_EXPIRED", "会话已过期"),
    AUTH_SERVICE_UNAVAILABLE("AUTH_SERVICE_UNAVAILABLE", "认证服务不可用");
    ;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    AuthErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
