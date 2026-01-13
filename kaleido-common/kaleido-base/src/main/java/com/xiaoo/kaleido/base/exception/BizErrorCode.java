package com.xiaoo.kaleido.base.exception;

import lombok.Getter;

/**
 * 业务错误码枚举
 * 定义系统通用的业务错误码
 *
 * @author ouyucheng
 * @date 2025/11/7
 */
@Getter
public enum BizErrorCode implements ErrorCode {


    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误"),
    PARAM_VALIDATION_FAILED("PARAM_VALIDATION_FAILED", "参数校验失败"),
    PERMISSION_DENIED("PERMISSION_DENIED", "权限不足"),
    DATA_NOT_EXIST("DATA_NOT_EXIST", "数据不存在"),
    UNIQUE_INDEX_CONFLICT("UNIQUE_INDEX_CONFLICT", "数据库唯一键冲突"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "资源未找到"),
    REQUEST_TIMEOUT("REQUEST_TIMEOUT", "请求超时"),
    NETWORK_ERROR("NETWORK_ERROR", "网络错误"),
    DATA_FORMAT_ERROR("DATA_FORMAT_ERROR", "数据格式错误"),
    OPERATION_NOT_ALLOWED("OPERATION_NOT_ALLOWED", "操作不允许"),
    SYSTEM_BUSY("SYSTEM_BUSY", "系统繁忙，请稍后重试"),
    ADMIN_NOT_LOGIN("ADMIN_NOT_LOGIN", "管理员未登录"),
    USER_NOT_LOGIN("USER_NOT_LOGIN", "用户未登录"),
    REQUEST_URL_NOT_FOUND("REQUEST_URL_NOT_FOUNT", "请求路径不存在"),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", "服务不可用");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    BizErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
