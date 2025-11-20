package com.xiaoo.kaleido.base.exception;

/**
 * @author ouyucheng
 * @date 2025/11/7
 * @description 错误码接口
 */
public interface ErrorCode {

    /**
     * 获取错误码
     * @return 错误码
     */
    String getCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMessage();
}
