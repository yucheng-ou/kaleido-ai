package com.xiaoo.kaleido.user.types.exception;

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
public class UserException  extends BizException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

}
