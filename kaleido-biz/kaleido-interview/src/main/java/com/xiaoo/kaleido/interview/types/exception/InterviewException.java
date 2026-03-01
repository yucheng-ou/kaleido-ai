package com.xiaoo.kaleido.interview.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 面试服务业务异常类
 * 面试服务中所有业务异常的基类，继承自BizException，用于统一处理面试相关的业务异常
 *
 * @author ouyucheng
 * @date 2026/2/28
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class InterviewException extends BizException {

    /**
     * 使用错误码构造异常
     *
     * @param errorCode 错误码枚举
     */
    public InterviewException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和错误信息构造异常
     *
     * @param errorCode 错误码字符串
     * @param errorMessage 错误信息
     */
    public InterviewException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    /**
     * 静态工厂方法：创建面试异常
     *
     * @param errorCode 错误码枚举
     * @return 面试异常实例
     */
    public static InterviewException of(ErrorCode errorCode) {
        return new InterviewException(errorCode);
    }

    /**
     * 静态工厂方法：创建面试异常
     *
     * @param errorCode 错误码字符串
     * @param errorMessage 错误信息
     * @return 面试异常实例
     */
    public static InterviewException of(String errorCode, String errorMessage) {
        return new InterviewException(errorCode, errorMessage);
    }

    /**
     * 静态工厂方法：创建面试异常（带自定义消息）
     *
     * @param errorCode 错误码枚举
     * @param message 自定义错误信息
     * @return 面试异常实例
     */
    public static InterviewException of(ErrorCode errorCode, String message) {
        return new InterviewException(errorCode.getCode(), message);
    }
}