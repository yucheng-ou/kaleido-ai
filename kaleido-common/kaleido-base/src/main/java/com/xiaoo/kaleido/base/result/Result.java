package com.xiaoo.kaleido.base.result;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import com.xiaoo.kaleido.base.response.ResponseCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ouyucheng
 * @date 2025/11/10
 * @description 统一结果封装
 */
@Getter
@Setter
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     * 200表示成功，其他表示失败
     */
    private String code;

    /**
     * 响应消息
     * 成功时为'成功'，失败时为错误信息
     */
    private String msg;

    /**
     * 是否成功
     * true表示成功，false表示失败
     */
    private Boolean success;

    /**
     * 响应数据
     * 成功时返回具体数据，失败时为null
     */
    private T data;

    public Result() {
    }

    public Result(String code, String msg, Boolean success, T data) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public Result(String code, String msg, Boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public static <T> Result<T> success() {
        return new Result<>(ResponseCode.SUCCESS.name(), ResponseCode.SUCCESS.name(), true);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseCode.SUCCESS.name(), ResponseCode.SUCCESS.name(), true, data);
    }


    public static <T> Result<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> Result<T> error(ErrorCode errorCode, String errorMsg) {
        return new Result<>(errorCode.getCode(), errorMsg, false);
    }

    public static <T> Result<T> error(String errorCode, String errorMsg) {
        return new Result<>(errorCode, errorMsg, false);
    }
}
