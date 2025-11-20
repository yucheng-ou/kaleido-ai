package com.xiaoo.kaleido.web.handler;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.response.ResponseCode;
import com.xiaoo.kaleido.web.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 全局异常处理器
 *
 * @author ouyucheng
 * @date 2025/11/10
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     *
     * @param bizException 业务异常对象，包含错误码和错误信息
     * @return 标准化错误响应 {@link Result<Void>}，包含错误码和错误消息
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result<Void> bizExceptionHandler(BizException bizException) {
        log.error("业务处理异常：", bizException);
        return Result.error(bizException);
    }

    /**
     * 处理所有未捕获的系统异常
     *
     * @param throwable 未捕获的异常对象
     * @return 标准化错误响应 {@link Result<Void>}，包含系统错误码和友好提示信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result<Void> throwableHandler(Throwable throwable) {
        log.error("系统异常：", throwable);
        return Result.error(ResponseCode.SERVER_INNER_ERROR.name(), "服务器忙碌，请稍后再试...");
    }
}
