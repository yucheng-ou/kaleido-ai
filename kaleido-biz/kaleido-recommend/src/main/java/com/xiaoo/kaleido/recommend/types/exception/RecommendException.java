package com.xiaoo.kaleido.recommend.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 推荐服务业务异常类
 * <p>
 * 推荐服务中所有业务异常的基类，继承自BizException，用于统一处理推荐相关的业务异常
 *
 * @author ouyucheng
 * @date 2026/1/26
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RecommendException extends BizException {

    /**
     * 使用错误码构造异常
     *
     * @param errorCode 错误码枚举
     */
    public RecommendException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和错误信息构造异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     */
    public RecommendException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 静态工厂方法：创建推荐异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     * @return 推荐异常实例
     */
    public static RecommendException of(String errorCode, String message) {
        return new RecommendException(errorCode, message);
    }

    /**
     * 静态工厂方法：创建推荐异常
     *
     * @param errorCode 错误码枚举
     * @return 推荐异常实例
     */
    public static RecommendException of(ErrorCode errorCode) {
        return new RecommendException(errorCode);
    }

    /**
     * 静态工厂方法：创建推荐异常（带自定义消息）
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误信息
     * @return 推荐异常实例
     */
    public static RecommendException of(ErrorCode errorCode, String message) {
        return new RecommendException(errorCode.getCode(), message);
    }
    
}
