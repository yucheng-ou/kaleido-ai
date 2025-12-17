package com.xiaoo.kaleido.notice.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;

/**
 * 通知服务业务异常
 *
 * @author ouyucheng
 * @date 2025/12/17
 */
public class NoticeException extends BizException {

    public NoticeException(String message) {
        super(message);
    }

    public NoticeException(String code, String message) {
        super(code, message);
    }

    public NoticeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoticeException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 创建通知异常
     *
     * @param errorCode 错误码
     * @return 通知异常
     */
    public static NoticeException of(NoticeErrorCode errorCode) {
        return new NoticeException(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 创建通知异常
     *
     * @param errorCode 错误码
     * @param message   错误信息
     * @return 通知异常
     */
    public static NoticeException of(NoticeErrorCode errorCode, String message) {
        return new NoticeException(errorCode.getCode(), message);
    }
}
