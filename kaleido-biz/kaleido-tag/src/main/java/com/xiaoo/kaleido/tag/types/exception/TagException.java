package com.xiaoo.kaleido.tag.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 标签服务业务异常类
 * <p>
 * 标签服务中所有业务异常的基类，继承自BizException，用于统一处理标签相关的业务异常
 *
 * @author ouyucheng
 * @date 2026/1/15
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TagException extends BizException {

    /**
     * 使用错误码构造异常
     *
     * @param errorCode 错误码枚举
     */
    public TagException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码和错误信息构造异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     */
    public TagException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 静态工厂方法：创建标签异常
     *
     * @param errorCode 错误码字符串
     * @param message   错误信息
     * @return 标签异常实例
     */
    public static TagException of(String errorCode, String message) {
        return new TagException(errorCode, message);
    }

    /**
     * 静态工厂方法：创建标签异常
     *
     * @param errorCode 错误码枚举
     * @return 标签异常实例
     */
    public static TagException of(ErrorCode errorCode) {
        return new TagException(errorCode);
    }

    /**
     * 静态工厂方法：创建标签异常（带自定义消息）
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误信息
     * @return 标签异常实例
     */
    public static TagException of(ErrorCode errorCode, String message) {
        return new TagException(errorCode.getCode(), message);
    }

    /**
     * 标签名称已存在异常
     *
     * @return 标签名称已存在异常实例
     */
    public static TagException tagNameExists() {
        return new TagException(TagErrorCode.TAG_NAME_EXISTS);
    }

}
