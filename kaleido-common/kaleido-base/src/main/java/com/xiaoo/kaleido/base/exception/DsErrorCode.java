package com.xiaoo.kaleido.base.exception;

import lombok.Getter;

/**
 * @author ouyucheng
 * @date 2025/11/7
 * @description
 */

@Getter
public enum DsErrorCode implements ErrorCode {

    /**
     * 数据库插入失败
     */
    INSERT_FAILED("INSERT_FAILED", "数据库插入失败"),

    /**
     * 数据库更新失败
     */
    UPDATE_FAILED("UPDATE_FAILED", "数据库更新失败"),
;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    DsErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
