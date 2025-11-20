package com.xiaoo.kaleido.base.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ouyucheng
 * @Date 2025/11/4 15:50
 * @Description 业务异常类
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

    private ErrorCode errorCode;
    private Map<String, Object> context;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.context = new HashMap<>();
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.context = new HashMap<>();
    }

    public BizException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.context = new HashMap<>();
    }

    public BizException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.context = new HashMap<>();
    }

    /**
     * 添加上下文信息
     *
     * @param key 键
     * @param value 值
     * @return 当前异常实例
     */
    public BizException withContext(String key, Object value) {
        this.context.put(key, value);
        return this;
    }

    /**
     * 获取上下文信息
     *
     * @return 上下文映射
     */
    public Map<String, Object> getContext() {
        return new HashMap<>(context);
    }

    public static BizException of(ErrorCode errorCode) {
        return new BizException(errorCode);
    }

    public static BizException of(ErrorCode errorCode, String message) {
        return new BizException(errorCode, message);
    }

    public static BizException of(ErrorCode errorCode, Throwable cause) {
        return new BizException(errorCode, cause);
    }

    public static BizException of(ErrorCode errorCode, String message, Throwable cause) {
        return new BizException(errorCode, message, cause);
    }
}
