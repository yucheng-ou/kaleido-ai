package com.xiaoo.kaleido.base.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ouyucheng
 * @Date 2025/11/4 15:50
 * @Description 业务异常类
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public BizException(String errorCode, String errorMessage) {
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public static BizException of(ErrorCode errorCode) {
        return new BizException(errorCode);
    }

    public static BizException of(String errorCode, String errorMessage) {
        return new BizException(errorCode, errorMessage);
    }

    public static BizException of(ErrorCode errorCode, String errorMessage) {
        return new BizException(errorCode.getCode(), errorMessage);
    }
}
