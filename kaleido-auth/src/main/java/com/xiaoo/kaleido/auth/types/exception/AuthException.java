package com.xiaoo.kaleido.auth.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 认证授权异常
 * <p>
 * 用于表示认证授权相关的业务异常，如登录失败、权限不足、token无效等
 *
 * @author ouyucheng
 * @date 2025/11/19
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AuthException extends BizException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    /**
     * 静态工厂方法：创建认证异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     * @return 认证异常实例
     */
    public static AuthException of(String errorCode, String message) {
        return new AuthException(errorCode, message);
    }

    public static AuthException of(ErrorCode errorCode) {
        return new AuthException(errorCode);
    }

}
