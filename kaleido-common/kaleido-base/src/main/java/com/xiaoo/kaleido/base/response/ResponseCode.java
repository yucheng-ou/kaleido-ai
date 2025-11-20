package com.xiaoo.kaleido.base.response;

/**
 * @author ouyucheng
 * @date 2025/11/10
 * @description
 */
public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS,

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT,

    /**
     * 重复
     */
    DUPLICATED,

    /**
     * 服务器内部错误
     */
    SERVER_INNER_ERROR,

    /**
     * 服务器繁忙
     */
    SERVER_BUSY,

    /**
     * 业务错误
     */
    BIZ_ERROR;
}