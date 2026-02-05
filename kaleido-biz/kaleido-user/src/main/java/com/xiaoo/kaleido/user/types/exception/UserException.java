package com.xiaoo.kaleido.user.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户服务业务异常类
 * <p>
 * 用户服务中所有业务异常的基类，继承自BizException，用于统一处理用户相关的业务异常
 *
 * @author ouyucheng
 * @date 2025/11/19
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserException extends BizException {

    /**
     * 使用错误码构造异常
     *
     * @param errorCode 错误码枚举
     */
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和错误信息构造异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     */
    public UserException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 静态工厂方法：创建用户异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     * @return 用户异常实例
     */
    public static UserException of(String errorCode, String message) {
        return new UserException(errorCode, message);
    }

    public static UserException of(UserErrorCode errorCode) {
        return new UserException(errorCode);
    }

}
