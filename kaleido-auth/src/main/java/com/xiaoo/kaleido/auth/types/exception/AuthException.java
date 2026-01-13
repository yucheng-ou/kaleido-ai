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

}
