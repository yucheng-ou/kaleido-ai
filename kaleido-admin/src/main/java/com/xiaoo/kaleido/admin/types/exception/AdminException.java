package com.xiaoo.kaleido.admin.types.exception;

import com.xiaoo.kaleido.base.exception.BizException;
import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 管理后台异常
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AdminException extends BizException {

    public AdminException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AdminException(String errorCode, String message) {
        super(errorCode, message);
    }

    public static AdminException of(String errorCode, String message) {
        return new AdminException(errorCode, message);
    }

    public static AdminException of(ErrorCode errorCode) {
        return new AdminException(errorCode.getCode(), errorCode.getMessage());
    }


    public static AdminException dictNotExist() {
        return new AdminException(AdminErrorCode.DICT_NOT_EXIST);
    }

    public static AdminException dictAlreadyExist() {
        return new AdminException(AdminErrorCode.DICT_ALREADY_EXIST);
    }

    public static AdminException dictTypeCodeExist() {
        return new AdminException(AdminErrorCode.DICT_TYPE_CODE_EXIST);
    }

    public static AdminException dictCodeExist() {
        return new AdminException(AdminErrorCode.DICT_CODE_EXIST);
    }

    public static AdminException dictStatusInvalid() {
        return new AdminException(AdminErrorCode.DICT_STATUS_INVALID);
    }

    public static AdminException dictOperateFailed() {
        return new AdminException(AdminErrorCode.DICT_OPERATE_FAILED);
    }

    public static AdminException adminPermissionDenied() {
        return new AdminException(AdminErrorCode.ADMIN_PERMISSION_DENIED);
    }

    public static AdminException adminOperationNotAllowed() {
        return new AdminException(AdminErrorCode.ADMIN_OPERATION_NOT_ALLOWED);
    }
}
