package com.xiaoo.kaleido.notice.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;

/**
 * 通知服务业务异常
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public class NoticeException extends BizException {

    public NoticeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoticeException(String errorCode, String message) {
        super(errorCode, message);
    }

    public static NoticeException of(String errorCode, String message) {
        return new NoticeException(errorCode, message);
    }

}
