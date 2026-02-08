package com.xiaoo.kaleido.message.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 消息服务业务异常类
 * <p>
 * 消息服务中所有业务异常的基类，继承自BizException，用于统一处理消息相关的业务异常
 *
 * @author ouyucheng
 * @date 2026/2/7
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MessageException extends BizException {

    /**
     * 使用错误码构造异常
     *
     * @param errorCode 错误码枚举
     */
    public MessageException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和错误信息构造异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     */
    public MessageException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 使用MessageErrorCode构造异常
     *
     * @param errorCode 消息错误码枚举
     */
    public MessageException(MessageErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用MessageErrorCode和自定义消息构造异常
     *
     * @param errorCode 消息错误码枚举
     * @param message   自定义错误信息
     */
    public MessageException(MessageErrorCode errorCode, String message) {
        super(errorCode.getCode(), message);
    }

    /**
     * 使用MessageErrorCode和原始异常构造异常
     *
     * @param errorCode 消息错误码枚举
     * @param cause     原始异常
     */
    public MessageException(MessageErrorCode errorCode, Throwable cause) {
        super(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 使用MessageErrorCode、自定义消息和原始异常构造异常
     *
     * @param errorCode 消息错误码枚举
     * @param message   自定义错误信息
     * @param cause     原始异常
     */
    public MessageException(MessageErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.getCode(), message);
    }

    /**
     * 静态工厂方法：创建消息异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     * @return 消息异常实例
     */
    public static MessageException of(String errorCode, String message) {
        return new MessageException(errorCode, message);
    }

    /**
     * 静态工厂方法：创建消息异常
     *
     * @param errorCode 错误码枚举
     * @return 消息异常实例
     */
    public static MessageException of(ErrorCode errorCode) {
        return new MessageException(errorCode);
    }

    /**
     * 静态工厂方法：创建消息异常
     *
     * @param errorCode 消息错误码枚举
     * @return 消息异常实例
     */
    public static MessageException of(MessageErrorCode errorCode) {
        return new MessageException(errorCode);
    }

    /**
     * 静态工厂方法：创建消息异常（带自定义消息）
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误信息
     * @return 消息异常实例
     */
    public static MessageException of(ErrorCode errorCode, String message) {
        return new MessageException(errorCode.getCode(), message);
    }

    /**
     * 静态工厂方法：创建消息异常（带自定义消息）
     *
     * @param errorCode 消息错误码枚举
     * @param message   自定义错误信息
     * @return 消息异常实例
     */
    public static MessageException of(MessageErrorCode errorCode, String message) {
        return new MessageException(errorCode, message);
    }

    /**
     * 静态工厂方法：创建消息异常（带原始异常）
     *
     * @param errorCode 消息错误码枚举
     * @param cause     原始异常
     * @return 消息异常实例
     */
    public static MessageException of(MessageErrorCode errorCode, Throwable cause) {
        return new MessageException(errorCode, cause);
    }

    /**
     * 静态工厂方法：创建消息异常（带自定义消息和原始异常）
     *
     * @param errorCode 消息错误码枚举
     * @param message   自定义错误信息
     * @param cause     原始异常
     * @return 消息异常实例
     */
    public static MessageException of(MessageErrorCode errorCode, String message, Throwable cause) {
        return new MessageException(errorCode, message, cause);
    }
}
