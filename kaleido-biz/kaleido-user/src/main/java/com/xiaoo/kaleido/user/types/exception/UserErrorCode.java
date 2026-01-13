package com.xiaoo.kaleido.user.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 用户服务错误码枚举
 * 
 * 定义用户服务中所有可能的错误码，包括参数校验、业务逻辑、状态异常等
 * 
 * @author ouyucheng
 * @date 2025/11/19
 */
@Getter
public enum UserErrorCode implements ErrorCode {

    /** 重复的电话号码：用户注册时手机号已存在 */
    DUPLICATE_TELEPHONE("DUPLICATE_TELEPHONE", "重复的电话号码"),
    
    /** 无效的邀请码：用户注册时提供的邀请码不存在或已失效 */
    INVALID_INVITE_CODE("INVALID_INVITE_CODE", "无效的邀请码"),
    
    /** 用户不存在：根据ID查询用户时未找到对应记录 */
    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),
    
    /** 用户已是活跃状态：尝试激活已活跃的用户 */
    USER_IS_ACTIVE("USER_IS_ACTIVE", "用户已是活跃状态"),
    
    /** 用户已被冻结：尝试操作已被冻结的用户 */
    USER_IS_FROZEN("USER_IS_FROZEN", "用户已被冻结"),
    
    /** 用户已被删除：尝试操作已被软删除的用户 */
    USER_IS_DELETED("USER_IS_DELETED", "用户已被删除"),
    
    /** 用户状态异常：用户状态不符合操作要求 */
    USER_STATUS_ERROR("USER_STATUS_ERROR", "用户状态异常"),
    
    /** 用户操作失败：通用用户操作失败错误 */
    USER_OPERATE_FAILED("USER_OPERATE_FAILED", "用户操作失败"),
    
    /** 用户密码错误：密码验证失败 */
    USER_PASSWD_ERROR("USER_PASSWD_ERROR", "用户密码错误"),
    
    /** 用户查询失败：用户查询操作失败 */
    USER_QUERY_FAIL("USER_QUERY_FAIL", "用户查询失败"),
    
    /** 用户名已存在：昵称已被其他用户使用 */
    NICK_NAME_EXIST("NICK_NAME_EXIST", "用户名已存在"),
    
    /** 用户id不能为空：用户ID参数为空 */
    USER_ID_NOT_NULL("USER_ID_NOT_NULL", "用户id不能为空"),
    
    /** 用户上传图片失败：头像上传操作失败 */
    USER_UPLOAD_PICTURE_FAIL("USER_UPLOAD_PICTURE_FAIL", "用户上传图片失败"),
    
    /** 请求参数不能为空：通用参数空值校验失败 */
    REQUEST_PARAM_NULL("REQUEST_PARAM_NULL", "请求参数不能为空"),
    
    /** 手机号不能为空：手机号参数为空 */
    MOBILE_EMPTY("MOBILE_EMPTY", "手机号不能为空"),
    
    /** 手机号格式错误：手机号不符合格式规范 */
    MOBILE_FORMAT_ERROR("MOBILE_FORMAT_ERROR", "手机号格式错误"),
    
    /** 密码不能为空：密码参数为空 */
    PASSWORD_EMPTY("PASSWORD_EMPTY", "密码不能为空"),
    
    /** 密码长度必须在6-20位之间：密码长度不符合要求 */
    PASSWORD_LENGTH_ERROR("PASSWORD_LENGTH_ERROR", "密码长度必须在6-20位之间"),
    
    /** 密码必须包含字母和数字：密码强度不足 */
    PASSWORD_WEAK("PASSWORD_WEAK", "密码必须包含字母和数字"),
    
    /** 密码哈希值不能为空：密码哈希参数为空 */
    PASSWORD_HASH_EMPTY("PASSWORD_HASH_EMPTY", "密码哈希值不能为空"),
    
    /** 用户ID不能为空：用户ID参数为空 */
    USER_ID_EMPTY("USER_ID_EMPTY", "用户ID不能为空"),
    
    /** 用户ID格式错误：用户ID不符合格式规范 */
    USER_ID_FORMAT_ERROR("USER_ID_FORMAT_ERROR", "用户ID格式错误"),
    
    /** 昵称不能为空：昵称参数为空 */
    NICK_NAME_EMPTY("NICK_NAME_EMPTY", "昵称不能为空"),
    
    /** 昵称长度必须在2-20位之间：昵称长度不符合要求 */
    NICK_NAME_LENGTH_ERROR("NICK_NAME_LENGTH_ERROR", "昵称长度必须在2-20位之间"),
    
    /** 昵称包含非法字符：昵称包含不允许的字符 */
    NICK_NAME_ILLEGAL_CHARS("NICK_NAME_ILLEGAL_CHARS", "昵称包含非法字符"),
    
    /** 邀请码不能为空：邀请码参数为空 */
    INVITE_CODE_EMPTY("INVITE_CODE_EMPTY", "邀请码不能为空"),
    
    /** 邀请码长度必须为6位：邀请码长度不符合要求 */
    INVITE_CODE_LENGTH_ERROR("INVITE_CODE_LENGTH_ERROR", "邀请码长度必须为6位"),
    
    /** 邀请码格式错误（只能包含字母和数字）：邀请码包含非法字符 */
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
