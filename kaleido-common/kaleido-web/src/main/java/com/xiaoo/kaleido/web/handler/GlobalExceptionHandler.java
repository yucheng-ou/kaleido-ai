package com.xiaoo.kaleido.web.handler;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.response.ResponseCode;
import com.xiaoo.kaleido.web.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public Result<Void> bizExceptionHandler(BizException bizException) {
        log.error("业务处理异常：", bizException);
        return Result.error(bizException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("请求参数异常：", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        Result<Map<String, String>> result = Result.error(ResponseCode.ILLEGAL_ARGUMENT.name(), "参数校验失败");
        result.setData(errors);
        return result;
    }


    /**
     * 处理所有未捕获的系统异常
     *
     * @param throwable 未捕获的异常对象
     * @return 标准化错误响应 {@link Result<Void>}，包含系统错误码和友好提示信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> throwableHandler(Throwable throwable) {
        // 使用反射判断是否是 Sentinel 的 BlockException
        // 避免直接依赖 Sentinel，保持模块独立性
        if (isBlockException(throwable)) {
            // 重新抛出，让 Sentinel 的 blockHandler 处理
            // BlockException 是 RuntimeException 的子类，所以可以安全转换
            throw (RuntimeException) throwable;
        }
        
        log.error("系统异常：", throwable);
        return Result.error(ResponseCode.SERVER_INNER_ERROR.name(), "服务器忙碌，请稍后再试...");
    }

    /**
     * 判断异常是否是 Sentinel 的 BlockException
     * 使用反射避免直接依赖 Sentinel
     */
    private boolean isBlockException(Throwable throwable) {
        try {
            // 使用类名判断，避免直接依赖
            Class<?> blockExceptionClass = Class.forName("com.alibaba.csp.sentinel.slots.block.BlockException");
            return blockExceptionClass.isInstance(throwable);
        } catch (ClassNotFoundException e) {
            // Sentinel 未引入，不是 BlockException
            return false;
        }
    }
}
