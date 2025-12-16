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
    USER_UPLOAD_PICTURE_FAIL("USER_UPLOAD_PICTURE_FAIL", "用户上传图片失败"),
    REQUEST_PARAM_NULL("REQUEST_PARAM_NULL", "请求参数不能为空"),
    MOBILE_EMPTY("MOBILE_EMPTY", "手机号不能为空"),
    MOBILE_FORMAT_ERROR("MOBILE_FORMAT_ERROR", "手机号格式错误"),
    PASSWORD_EMPTY("PASSWORD_EMPTY", "密码不能为空"),
    PASSWORD_LENGTH_ERROR("PASSWORD_LENGTH_ERROR", "密码长度必须在6-20位之间"),
    PASSWORD_WEAK("PASSWORD_WEAK", "密码必须包含字母和数字"),
    PASSWORD_HASH_EMPTY("PASSWORD_HASH_EMPTY", "密码哈希值不能为空"),
    USER_ID_EMPTY("USER_ID_EMPTY", "用户ID不能为空"),
    USER_ID_FORMAT_ERROR("USER_ID_FORMAT_ERROR", "用户ID格式错误"),
    NICK_NAME_EMPTY("NICK_NAME_EMPTY", "昵称不能为空"),
    NICK_NAME_LENGTH_ERROR("NICK_NAME_LENGTH_ERROR", "昵称长度必须在2-20位之间"),
    NICK_NAME_ILLEGAL_CHARS("NICK_NAME_ILLEGAL_CHARS", "昵称包含非法字符"),
    INVITE_CODE_EMPTY("INVITE_CODE_EMPTY", "邀请码不能为空"),
    INVITE_CODE_LENGTH_ERROR("INVITE_CODE_LENGTH_ERROR", "邀请码长度必须为6位"),
    INVITE_CODE_FORMAT_ERROR("INVITE_CODE_FORMAT_ERROR", "邀请码格式错误（只能包含字母和数字）");

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
