package com.xiaoo.kaleido.wardrobe.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 衣柜服务业务异常类
 * <p>
 * 衣柜服务中所有业务异常的基类，继承自BizException，用于统一处理衣柜相关的业务异常
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class WardrobeException extends BizException {

    /**
     * 使用错误码构造异常
     *
     * @param errorCode 错误码枚举
     */
    public WardrobeException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和错误信息构造异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     */
    public WardrobeException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 静态工厂方法：创建衣柜异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     * @return 衣柜异常实例
     */
    public static WardrobeException of(String errorCode, String message) {
        return new WardrobeException(errorCode, message);
    }

    /**
     * 静态工厂方法：创建衣柜异常
     *
     * @param errorCode 错误码枚举
     * @return 衣柜异常实例
     */
    public static WardrobeException of(ErrorCode errorCode) {
        return new WardrobeException(errorCode);
    }

    /**
     * 静态工厂方法：创建衣柜异常（带自定义消息）
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误信息
     * @return 衣柜异常实例
     */
    public static WardrobeException of(ErrorCode errorCode, String message) {
        return new WardrobeException(errorCode.getCode(), message);
    }
    
}
