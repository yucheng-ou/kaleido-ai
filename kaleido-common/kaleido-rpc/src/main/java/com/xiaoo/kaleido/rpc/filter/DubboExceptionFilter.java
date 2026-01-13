package com.xiaoo.kaleido.rpc.filter;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.response.ResponseCode;
import com.xiaoo.kaleido.base.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import jakarta.validation.ValidationException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Dubbo异常过滤器
 * 统一处理Dubbo服务提供者的异常，将异常转换为标准Result响应
 * 解决跨服务异常传播导致的ClassNotFoundException问题
 *
 * @author ouyucheng
 * @date 2026/1/7
 */
@Slf4j
@Activate(group = {CommonConstants.PROVIDER}, order = -10000)
public class DubboExceptionFilter implements Filter {

    @Override
    public org.apache.dubbo.rpc.Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            org.apache.dubbo.rpc.Result result = invoker.invoke(invocation);

            // 检查结果是否有异常
            if (result.hasException()) {
                return handleResultException(result.getException(), invoker, invocation);
            }

            return result;
        } catch (Throwable e) {
            // 捕获所有Throwable，确保不遗漏任何异常
            return handleException(e, invoker, invocation);
        }
    }

    private org.apache.dubbo.rpc.Result handleResultException(Throwable e, Invoker<?> invoker, Invocation invocation) {
        log.debug("处理Dubbo结果中的异常，服务: {}, 方法: {}",
                invoker.getInterface().getSimpleName(), invocation.getMethodName());
        return convertExceptionToResult(e, invoker, invocation);
    }

    private org.apache.dubbo.rpc.Result handleException(Throwable e, Invoker<?> invoker, Invocation invocation) {
        log.debug("处理Dubbo调用过程中的异常，服务: {}, 方法: {}",
                invoker.getInterface().getSimpleName(), invocation.getMethodName());
        return convertExceptionToResult(e, invoker, invocation);
    }

    private org.apache.dubbo.rpc.Result convertExceptionToResult(Throwable e, Invoker<?> invoker, Invocation invocation) {
        // 1. 解包UndeclaredThrowableException（AOP代理可能包装的异常）
        Throwable cause = unwrapException(e);

        // 2. 记录异常详情
        logExceptionDetails(cause, invoker, invocation);

        // 3. 转换为Result对象
        Result<String> result = createResultFromException(cause);

        // 4. 返回干净的AsyncRpcResult，避免序列化异常类
        return AsyncRpcResult.newDefaultAsyncResult(result, invocation);
    }

    private Throwable unwrapException(Throwable e) {
        Throwable cause = e;

        // 1. 解包UndeclaredThrowableException
        while (cause instanceof UndeclaredThrowableException) {
            cause = ((UndeclaredThrowableException) cause).getUndeclaredThrowable();
        }

        // 2. 获取根本原因
        while (cause != null && cause.getCause() != null && cause.getCause() != cause) {
            cause = cause.getCause();
        }

        return cause != null ? cause : e;
    }

    private void logExceptionDetails(Throwable e, Invoker<?> invoker, Invocation invocation) {
        String serviceName = invoker.getInterface().getSimpleName();
        String methodName = invocation.getMethodName();

        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.warn("业务异常，服务: {}, 方法: {}, 错误码: {}, 错误信息: {}",
                    serviceName, methodName,
                    bizException.getErrorCode(),
                    bizException.getErrorMessage());
        } else if (e instanceof ValidationException) {
            log.warn("参数验证异常，服务: {}, 方法: {}, 异常信息: {}",
                    serviceName, methodName, e.getMessage());
        } else if (e instanceof RpcException) {
            log.error("Dubbo RPC异常，服务: {}, 方法: {}", serviceName, methodName, e);
        } else {
            log.error("系统异常，服务: {}, 方法: {}", serviceName, methodName, e);
        }
    }

    /**
     * 根据异常封装统一Result
     * 返回Result而不是直接抛出异常，因为调用方不一定是Java服务
     *
     * @param e 异常信息
     * @return 统一的错误Result
     */
    private Result<String> createResultFromException(Throwable e) {
        if (e instanceof BizException bizException) {
            return Result.error(
                    bizException.getErrorCode(),
                    bizException.getErrorMessage()
            );
        } else if (e instanceof ValidationException) {
            // 参数验证异常 - 只取第一个错误信息
            String errorMessage = extractFirstValidationMessage(e);
            return Result.error(
                    ResponseCode.ILLEGAL_ARGUMENT.name(),
                    errorMessage
            );
        } else if (e instanceof RpcException) {
            // Dubbo框架异常
            return Result.error(
                    ResponseCode.SERVER_INNER_ERROR.name(),
                    "远程服务调用失败，请稍后重试"
            );
        } else {
            // 其他系统异常
            return Result.error(
                    ResponseCode.SERVER_INNER_ERROR.name(),
                    "服务器内部错误，请稍后重试"
            );
        }
    }

    /**
     * 从ValidationException中提取第一个错误信息
     * 当多个校验规则同时失败时，只返回第一个错误信息
     *
     * @param e ValidationException异常
     * @return 第一个错误信息
     */
    private String extractFirstValidationMessage(Throwable e) {
        String fullMessage = e.getMessage();
        if (fullMessage == null || fullMessage.isEmpty()) {
            return "参数校验失败";
        }

        // 如果消息包含多个错误（用逗号分隔），只取第一个
        // 例如："register.command.username: 管理员账号不能为空, register.command.username: 管理员账号长度必须在4-20位之间"
        // 应该只返回："管理员账号不能为空"
        
        // 1. 首先，查找第一个冒号后的内容
        int firstColonIndex = fullMessage.indexOf(':');
        if (firstColonIndex == -1) {
            // 没有冒号，直接返回原消息
            return fullMessage;
        }
        
        // 2. 获取第一个冒号后的部分
        String afterFirstColon = fullMessage.substring(firstColonIndex + 1).trim();
        
        // 3. 如果还有逗号，只取逗号前的部分
        int firstCommaIndex = afterFirstColon.indexOf(',');
        if (firstCommaIndex != -1) {
            // 3.1 找到第一个错误信息（逗号前的内容）
            String firstError = afterFirstColon.substring(0, firstCommaIndex).trim();
            
            // 3.2 清理可能的多余前缀（如字段名重复）
            // 例如："管理员账号不能为空, register.command.username: 管理员账号长度必须在4-20位之间"
            // 我们需要确保只返回纯错误信息
            
            // 检查是否还包含其他字段前缀（冒号）
            int anotherColonIndex = firstError.indexOf(':');
            if (anotherColonIndex != -1) {
                // 如果还有冒号，取最后一个冒号后的内容
                return firstError.substring(anotherColonIndex + 1).trim();
            }
            return firstError;
        }
        
        // 4. 没有逗号，直接返回第一个冒号后的内容
        // 但需要检查是否还包含其他字段前缀
        int anotherColonIndex = afterFirstColon.indexOf(':');
        if (anotherColonIndex != -1) {
            // 如果还有冒号，取最后一个冒号后的内容
            return afterFirstColon.substring(anotherColonIndex + 1).trim();
        }
        
        return afterFirstColon;
    }
}
