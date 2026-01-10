package com.xiaoo.kaleido.admin.types.exception;

import com.xiaoo.kaleido.base.exception.ErrorCode;
import lombok.Getter;

/**
 * 管理后台错误码
 *
 * @author ouyucheng
 * @date 2025/12/25
 */
@Getter
public enum AdminErrorCode implements ErrorCode {

    // 字典管理相关错误码
    DICT_NOT_EXIST("DICT_NOT_EXIST", "字典不存在"),
    DICT_ALREADY_EXIST("DICT_ALREADY_EXIST", "字典已存在"),
    DICT_TYPE_CODE_EXIST("DICT_TYPE_CODE_EXIST", "字典类型编码已存在"),
    DICT_CODE_EXIST("DICT_CODE_EXIST", "字典编码已存在"),
    DICT_STATUS_INVALID("DICT_STATUS_INVALID", "字典状态无效"),
    DICT_OPERATE_FAILED("DICT_OPERATE_FAILED", "字典操作失败"),
    DICT_TYPE_CODE_EMPTY("DICT_TYPE_CODE_EMPTY", "字典类型编码不能为空"),
    DICT_TYPE_NAME_EMPTY("DICT_TYPE_NAME_EMPTY", "字典类型名称不能为空"),
    DICT_CODE_EMPTY("DICT_CODE_EMPTY", "字典编码不能为空"),
    DICT_NAME_EMPTY("DICT_NAME_EMPTY", "字典名称不能为空"),
    DICT_SORT_INVALID("DICT_SORT_INVALID", "字典排序值无效"),

    // 通用管理错误码
    ADMIN_PERMISSION_DENIED("ADMIN_PERMISSION_DENIED", "管理员权限不足"),
    ADMIN_OPERATION_NOT_ALLOWED("ADMIN_OPERATION_NOT_ALLOWED", "管理员操作不允许"),
    ADMIN_DATA_VALIDATION_FAILED("ADMIN_DATA_VALIDATION_FAILED", "数据验证失败"),
    ADMIN_SYSTEM_ERROR("ADMIN_SYSTEM_ERROR", "管理系统错误"),

    // 系统配置错误码（为后续扩展预留）
    CONFIG_NOT_EXIST("CONFIG_NOT_EXIST", "配置不存在"),
    CONFIG_VALUE_INVALID("CONFIG_VALUE_INVALID", "配置值无效"),
    CONFIG_KEY_EXIST("CONFIG_KEY_EXIST", "配置键已存在"),

    // 用户管理错误码（为后续扩展预留）
    ADMIN_USER_NOT_EXIST("ADMIN_USER_NOT_EXIST", "管理员用户不存在"),
    ADMIN_USER_DISABLED("ADMIN_USER_DISABLED", "管理员用户已禁用"),
    ADMIN_USER_STATUS_ERROR("ADMIN_USER_STATUS_ERROR", "管理员用户状态异常"),
    ADMIN_USER_PASSWORD_ERROR("ADMIN_USER_PASSWORD_ERROR", "管理员密码错误"),
    ADMIN_USERNAME_EXIST("ADMIN_USERNAME_EXIST", "管理员账号已存在"),
    ADMIN_MOBILE_EXIST("ADMIN_MOBILE_EXIST", "手机号已存在"),

    // 角色权限错误码（为后续扩展预留）
    ROLE_NOT_EXIST("ROLE_NOT_EXIST", "角色不存在"),
    ROLE_ALREADY_EXIST("ROLE_ALREADY_EXIST", "角色已存在"),
    ROLE_CODE_EXIST("ROLE_CODE_EXIST", "角色编码已存在"),
    ROLE_DISABLED("ROLE_DISABLED", "角色已禁用"),
    PERMISSION_NOT_EXIST("PERMISSION_NOT_EXIST", "权限不存在"),
    PERMISSION_CODE_EXIST("PERMISSION_CODE_EXIST", "权限编码已存在"),
    PARENT_PERMISSION_NOT_EXIST("PARENT_PERMISSION_NOT_EXIST", "父权限不存在"),
    PERMISSION_HAS_CHILDREN("PERMISSION_HAS_CHILDREN", "存在子权限，无法删除");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    AdminErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
