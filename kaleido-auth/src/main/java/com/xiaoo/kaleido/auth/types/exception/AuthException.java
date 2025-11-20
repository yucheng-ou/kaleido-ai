package com.xiaoo.kaleido.auth.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AuthException  extends BizException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }

}
