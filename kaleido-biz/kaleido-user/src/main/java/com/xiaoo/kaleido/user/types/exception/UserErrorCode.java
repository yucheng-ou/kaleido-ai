package com.xiaoo.kaleido.user.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * @author ouyucheng
 * @date 2025/11/19
 * @description
 */
@Getter
public enum UserErrorCode implements ErrorCode {

    DUPLICATE_TELEPHONE("DUPLICATE_TELEPHONE", "重复的电话号码"),
    INVALID_INVITE_CODE("INVALID_INVITE_CODE", "无效的邀请码"),
    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),
    USER_IS_FROZEN("USER_IS_FROZEN", "用户被冻结"),
    USER_OPERATE_FAILED("USER_OPERATE_FAILED", "用户操作失败"),
    USER_PASSWD_ERROR("USER_PASSWD_ERROR", "用户密码错误"),
    USER_QUERY_FAIL("USER_QUERY_FAIL", "用户查询失败"),
    NICK_NAME_EXIST("NICK_NAME_EXIST", "用户名已存在"),
    USER_ID_NOT_NULL("USER_ID_NOT_NULL", "用户id不能为空"),
    USER_UPLOAD_PICTURE_FAIL("USER_UPLOAD_PICTURE_FAIL", "用户上传图片失败");    ;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    UserErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
