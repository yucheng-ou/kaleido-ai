package com.xiaoo.kaleido.admin.types.exception.hander;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import com.xiaoo.kaleido.admin.types.exception.AdminErrorCode;
import com.xiaoo.kaleido.base.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 管理后台全局异常处理器
 * 专门处理 Sa-Token 相关的异常
 *
 * @author ouyucheng
 * @date 2026/1/12
 */
@RestControllerAdvice
@Slf4j
public class AdminGlobalExceptionHandler {

    /**
     * 处理权限不足异常
     */
    @ExceptionHandler(NotPermissionException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleNotPermissionException(NotPermissionException e) {
        log.warn("权限校验失败：{}", e.getMessage());
        return Result.error(AdminErrorCode.ADMIN_PERMISSION_DENIED.getCode(),
                          "权限不足：" + e.getMessage());
    }

    /**
     * 处理角色校验失败异常
     */
    @ExceptionHandler(NotRoleException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleNotRoleException(NotRoleException e) {
        log.warn("角色校验失败：{}", e.getMessage());
        return Result.error("ADMIN_ROLE_DENIED", "角色权限不足：" + e.getMessage());
    }

    /**
     * 处理其他 Sa-Token 异常
     */
    @ExceptionHandler(SaTokenException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleSaTokenException(SaTokenException e) {
        log.error("Sa-Token 异常：{}", e.getMessage(), e);
        return Result.error("SA_TOKEN_ERROR", "认证授权异常：" + e.getMessage());
    }
}
