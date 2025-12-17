package com.xiaoo.kaleido.web.result;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import com.xiaoo.kaleido.base.response.ResponseCode;
import com.xiaoo.kaleido.base.response.SingleResp;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ouyucheng
 * @date 2025/11/10
 * @description
 */
@Getter
@Setter
public class Result<T> {

    private String code;

    private String msg;

    private Boolean success;

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

    public Result(SingleResp<T> singleResp) {
        this.success = singleResp.getSuccess();
        this.data = singleResp.getData();
        this.code = singleResp.getResponseCode();
        this.msg = singleResp.getResponseMsg();
    }

    public static <T> Result<T> success() {
        return new Result<>(ResponseCode.SUCCESS.name(), ResponseCode.SUCCESS.name(), true);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseCode.SUCCESS.name(), ResponseCode.SUCCESS.name(), true, data);
    }

    public static <T> Result<T> error(BizException bizException) {
        return error(bizException.getErrorCode(), bizException.getMessage());
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> Result<T> error(String errorCode, String errorMsg) {
        return new Result<>(errorCode, errorMsg, false);
    }
}
