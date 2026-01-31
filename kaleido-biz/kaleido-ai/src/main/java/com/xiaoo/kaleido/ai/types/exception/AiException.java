package com.xiaoo.kaleido.ai.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * AI服务业务异常类
 * AI服务中所有业务异常的基类，继承自BizException，用于统一处理AI相关的业务异常
 *
 * @author ouyucheng
 * @date 2026/1/30
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AiException extends BizException {

    /**
     * 使用错误码构造异常
     *
     * @param errorCode 错误码枚举
     */
    public AiException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和错误信息构造异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     */
    public AiException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 静态工厂方法：创建AI异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     * @return AI异常实例
     */
    public static AiException of(String errorCode, String message) {
        return new AiException(errorCode, message);
    }

    /**
     * 静态工厂方法：创建AI异常
     *
     * @param errorCode 错误码枚举
     * @return AI异常实例
     */
    public static AiException of(ErrorCode errorCode) {
        return new AiException(errorCode);
    }

    /**
     * 静态工厂方法：创建AI异常（带自定义消息）
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误信息
     * @return AI异常实例
     */
    public static AiException of(ErrorCode errorCode, String message) {
        return new AiException(errorCode.getCode(), message);
    }
}
